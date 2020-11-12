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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

import com.mju.app.demo.entity.Category;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;
import com.mju.app.demo.repo.PlaceDetailRepository;
import com.mju.app.demo.repo.RoomRepository;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/PlaceDetail")
public class PlaceDetailController {
	@Autowired
	PlaceDetailRepository mPlaceDetailRepository;
	
	@PostMapping(path="addimageplacedetail")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/"+file.getOriginalFilename());
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
	
	@PostMapping(path="addplacedetails")
	public @ResponseBody ResponseObj addPlaceDetail(@RequestBody List<PlaceDetail> placedetails)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		System.out.println(placedetails.size());
		List<PlaceDetail> pp = new ArrayList<>();
		for(PlaceDetail p : placedetails) {
			System.out.println(p.toString());
			PlaceDetail place = new PlaceDetail();
			List<LocationPlace> lp = locationplace();
			place.setPlaceDetailName(p.getPlaceDetailName());
			place.setPlaceDetail_Detail(p.getPlaceDetail_Detail());
			place.setPlaceDetail_Image(p.getPlaceDetail_Image());
			place.setLocationPlace(lp.get(0));
			System.out.println("TESTDATA::"+place.toString());	
			pp.add(place);
		}
		mPlaceDetailRepository.saveAll(pp);
		return new ResponseObj(200, pp);
			
	}
	
	@PostMapping(path="addmoreplacedetails")
	public @ResponseBody ResponseObj addmoreplacedetails(@RequestBody List<PlaceDetail> placedetails)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		System.out.println(placedetails.size());
		List<PlaceDetail> pp = new ArrayList<>();
		for(PlaceDetail p : placedetails) {
			System.out.println(p.toString());
			PlaceDetail place = new PlaceDetail();
			LocationPlace lp = locationplacebyID(p.getPlaceDetailID());
			place.setPlaceDetailName(p.getPlaceDetailName());
			place.setPlaceDetail_Detail(p.getPlaceDetail_Detail());
			place.setPlaceDetail_Image(p.getPlaceDetail_Image());
			place.setLocationPlace(lp);
			System.out.println("TESTDATA::"+place.toString());	
			pp.add(place);
		}
		StatusVersion s = mPlaceDetailRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mPlaceDetailRepository.UpdateStatusVersion(""+v);
		mPlaceDetailRepository.saveAll(pp);
		return new ResponseObj(200, pp);
			
	}

	@PostMapping(path="/deleteimageplace")
	public @ResponseBody ResponseObj deleteimageplace(@RequestBody PlaceDetail pd) throws IOException{
		FileUtils.forceDelete(new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/"+pd.getPlaceDetail_Image()));  
		return null;
	}
//	
//	public List<PlaceDetail> getPlaceDetail(int l) {
//		List<PlaceDetail> pd = mPlaceDetailRepository.getPlaceByLocationID(l);
//		return pd;
//	}
	
	public LocationPlace locationplacebyID(int locationID) {
		return mPlaceDetailRepository.getLocationPlaceByID(locationID);
	}
	
	public List<LocationPlace> locationplace() {
		if( mPlaceDetailRepository.getLocationPlace(new PageRequest(0,1)) != null) {
			List<LocationPlace> lp = mPlaceDetailRepository.getLocationPlace(new PageRequest(0,1));
			return  lp ;
		}
		else {
			return null;
		}
		
	}
	
	@PostMapping(path="/getPlaceByLocationID")
	public @ResponseBody ResponseObj getPlaceByLocationID(@RequestBody LocationPlace location) {
		System.out.println("data:"+location.toString());
		List<PlaceDetail> list = mPlaceDetailRepository.getPlaceByLocationID(location.getLocationID());
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/getListPlaceDetail")
	public @ResponseBody ResponseObj getListPlaceDetail() {
		List<PlaceDetail> list = mPlaceDetailRepository.getListPlaceDetail();
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="/editPlace")
	public @ResponseBody ResponseObj editPlace(@RequestBody PlaceDetail p)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(p.toString());
		int placeDetailID = p.getPlaceDetailID();
		String placeDetailName = p.getPlaceDetailName();
		String placeDetail_Detail = p.getPlaceDetail_Detail();
		if(placeDetailName==""){
			PlaceDetail place = mPlaceDetailRepository.getPlaceDetailByID(placeDetailID);
			placeDetailName = place.getPlaceDetailName();
		}else {
			if(placeDetail_Detail=="") {
				PlaceDetail place = mPlaceDetailRepository.getPlaceDetailByID(placeDetailID);
				placeDetail_Detail = place.getPlaceDetail_Detail();
				mPlaceDetailRepository.EditPlace(placeDetailID, placeDetailName, placeDetail_Detail);
			}else {
				mPlaceDetailRepository.EditPlace(placeDetailID, placeDetailName, placeDetail_Detail);
			}
		}
		StatusVersion s = mPlaceDetailRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mPlaceDetailRepository.UpdateStatusVersion(""+v);
		PlaceDetail place = mPlaceDetailRepository.getPlaceDetailByID(placeDetailID);
		return new ResponseObj(200,place);
	}
	
	@PostMapping(path="/DeletePlace")
	public @ResponseBody ResponseObj DeletePlace(@RequestBody PlaceDetail p)
			throws NoSuchAlgorithmException, IOException {
		System.out.println(p.toString());
		int placeDetailID = p.getPlaceDetailID();
		StatusVersion s = mPlaceDetailRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mPlaceDetailRepository.UpdateStatusVersion(""+v);
		deleteimageplace(p);
		mPlaceDetailRepository.DeletePlace(placeDetailID);
		return new ResponseObj(200,"Success");
	}
	
}
