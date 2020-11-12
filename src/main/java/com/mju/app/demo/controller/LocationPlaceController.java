package com.mju.app.demo.controller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.mju.app.demo.util.ResponseObj;
import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;
import com.mju.app.demo.repo.LocationPlaceRepository;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/LocationPlace")
public class LocationPlaceController {

	@Autowired
	LocationPlaceRepository mLocationPlaceRepository;
	
	@PostMapping(path="/getListLocationPlace")
	public @ResponseBody ResponseObj getListLocationPlace() {
		List<LocationPlace> list = mLocationPlaceRepository.getListLocationPlace();
		return new ResponseObj(200,list);
	}
		
	public LocationPlace getlocation(int l) {
		LocationPlace location = mLocationPlaceRepository.getLocationPlaceByID(l);
		return location;
		
	}
	
	public List<PlaceDetail> getPlaceDetail(int l) {
		List<PlaceDetail> pd = mLocationPlaceRepository.getPlaceByLocationID(l);
		return pd;
	}
	
	@PostMapping(path="addlocation")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/photoLocations/"+file.getOriginalFilename());
		convertFile.createNewFile();
		BufferedImage temp = null;
		Image img = ImageIO.read(convertFile);
		temp = resizeImage(img,100,100);
		
		File newFile = convertFile;
		ImageIO.write(temp, "jpg", newFile);
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
	}
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
	}
	
	@PostMapping(path="addlocations")
	public @ResponseBody ResponseObj addLocations(@RequestBody LocationPlace locationplace)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		LocationPlace locationplaces = new LocationPlace();
		System.out.println(locationplace.toString());
		Category cat = CategoryLocation(locationplace.getLocationID());
		System.out.println(cat.getCategoryName());
		locationplaces.setLocationName(locationplace.getLocationName());
		locationplaces.setLocationDetails(locationplace.getLocationDetails());
		locationplaces.setImageLocationPath(locationplace.getLocationName()+"image");
		locationplaces.setLatitude(locationplace.getLatitude());
		locationplaces.setLongtitude(locationplace.getLongtitude());
		locationplaces.setCategory(cat);
		if(mLocationPlaceRepository.checkDup(locationplaces.getLocationID()) != null) {
			return new ResponseObj(500, "Dupplicated user!!");
		}else {
			StatusVersion s = mLocationPlaceRepository.getStatusVersion();
			int v = Integer.parseInt(s.getStatusVersionName())+1;
			mLocationPlaceRepository.UpdateStatusVersion(""+v);
			mLocationPlaceRepository.save(locationplaces);
			return new ResponseObj(200, locationplaces);
		}
	}
	
	public Category CategoryLocation(int categoryid) {
		if( mLocationPlaceRepository.checkDup2(categoryid) != null) {
			Category category = mLocationPlaceRepository.checkDup2(categoryid);
			return  category ;
		}
		else {
			return null;
		}
		
	}
	@PostMapping(path="/getLocationPlace")
	public @ResponseBody ResponseObj getLocationPlace(@RequestBody Category category) {
		System.out.println("data:"+category.toString());
		List<LocationPlace> list = mLocationPlaceRepository.getLocationPlace(category.getCategoryID());
		return new ResponseObj(200,list);
	}
	@PostMapping(path="/getLocationPlaceByID")
	public @ResponseBody ResponseObj getLocationPlace(@RequestBody LocationPlace location) {
		System.out.println("data2:"+location.toString());
		LocationPlace list = mLocationPlaceRepository.getLocationPlaceByID(location.getLocationID());
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/editLocationPlace")
	public @ResponseBody ResponseObj editLocationPlace(@RequestBody LocationPlace lp)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		int LocationID = lp.getLocationID();
		String LocationName = lp.getLocationName();
		String LocationDetails = lp.getLocationDetails();
		
		mLocationPlaceRepository.EditLocationPlace(LocationID, LocationName, LocationDetails);
		StatusVersion s = mLocationPlaceRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mLocationPlaceRepository.UpdateStatusVersion(""+v);
		LocationPlace location = mLocationPlaceRepository.getLocationPlaceByID(LocationID);
		return new ResponseObj(200,location);
	}
	
	@PostMapping(path="/DeleteLocation")
	public @ResponseBody ResponseObj DeleteLocation(@RequestBody LocationPlace lp)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(lp.toString());
		int LocationID = lp.getLocationID();
		try {
			StatusVersion s = mLocationPlaceRepository.getStatusVersion();
			int v = Integer.parseInt(s.getStatusVersionName())+1;
			mLocationPlaceRepository.UpdateStatusVersion(""+v);
			LocationPlace location = getlocation(LocationID);
			List<PlaceDetail> p = getPlaceDetail(LocationID);
			mLocationPlaceRepository.DeleteLocation(LocationID);
			FileUtils.forceDelete(new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/photoLocations/"+location.getImageLocationPath()+".jpg"));
			for(PlaceDetail pp : p) {
				FileUtils.forceDelete(new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/"+pp.getPlaceDetail_Image()));  
			}
			return new ResponseObj(200,"Success");
		}catch(Exception e){
			return new ResponseObj(500,"error");
		}
	}
	
	@PostMapping(path="/SearchLocationPlace")
	public @ResponseBody ResponseObj SearchLocationPlace(@RequestBody LocationPlace location) {
		System.out.println("data2:"+location.toString());
		String s = location.getLocationName();
		List<LocationPlace> list = mLocationPlaceRepository.SearchLocationPlace('%'+s+'%');
		return new ResponseObj(200,list);
	}
	
}
