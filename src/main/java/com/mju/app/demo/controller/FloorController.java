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

import com.mju.app.demo.entity.Floor;
import com.mju.app.demo.entity.LocationPlace;
import com.mju.app.demo.entity.PlaceDetail;
import com.mju.app.demo.entity.StatusVersion;
import com.mju.app.demo.repo.FloorRepository;
import com.mju.app.demo.util.ResponseObj;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/Floor")
public class FloorController {
	@Autowired
	FloorRepository mFloorRepository;
	
	@PostMapping(path="/getListFloor")
	public @ResponseBody ResponseObj getListFloor() {
		List<Floor> list = mFloorRepository.getListFloor();
		return new ResponseObj(200,list);
	}
	
	@PostMapping(path="uploadImageFloor")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/imageFloor/"+file.getOriginalFilename());
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
	@PostMapping(path="/deleteimagefloor")
	public @ResponseBody ResponseObj deleteimagefloor(@RequestBody Floor f) throws IOException{
		System.out.println(f.getImagePlanPath());
		FileUtils.forceDelete(new File("/Users/mac/Documents/backup/Eclipse-project/MJUAppService/src/main/resources/static/images/imageFloor/"+f.getImagePlanPath()));  
		return null;
	}
	
	@PostMapping(path="AddFloor")
	public @ResponseBody ResponseObj AddFloor(@RequestBody List<Floor> floordata)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {

		List<Floor> f = new ArrayList<>();
		for(Floor ff : floordata) {
			System.out.println(ff.toString());
			Floor floor = new Floor();
			List<LocationPlace> lp = locationplace();
			floor.setFloorName(ff.getFloorName());
				if(ff.getImagePlanPath().equals("-")) {
					floor.setImagePlanPath("building_temp.jpg");
				}else {
					floor.setImagePlanPath(ff.getImagePlanPath());
				}
			floor.setLocationPlace(lp.get(0));
			f.add(floor);
		}
		mFloorRepository.saveAll(f);
		return new ResponseObj(200, f);
			
	}
	
	@PostMapping(path="AddmoreFloor")
	public @ResponseBody ResponseObj AddmoreFloor(@RequestBody List<Floor> floordata)
			throws NoSuchAlgorithmException, UnsupportedEncodingException  {

		List<Floor> f = new ArrayList<>();
		for(Floor ff : floordata) {
			System.out.println(ff.toString());
			Floor floor = new Floor();
			LocationPlace lp = locationbyID(ff.getFloorID());
			floor.setFloorName(ff.getFloorName());
				if(ff.getImagePlanPath().equals("-")) {
					floor.setImagePlanPath("building_temp.jpg");
				}else {
					floor.setImagePlanPath(ff.getImagePlanPath());
				}
			floor.setLocationPlace(lp);
			f.add(floor);
		}
		StatusVersion s = mFloorRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mFloorRepository.UpdateStatusVersion(""+v);
		mFloorRepository.saveAll(f);
		return new ResponseObj(200, f);
			
	}
	
	public LocationPlace locationbyID(int locationid) {
		return mFloorRepository.getLocationPlaceByID(locationid);
	}
	
	public List<LocationPlace> locationplace() {
		if( mFloorRepository.getLocationPlace(new PageRequest(0,1)) != null) {
			List<LocationPlace> lp = mFloorRepository.getLocationPlace(new PageRequest(0,1));
			return  lp ;
		}
		else {
			return null;
		}
		
	}
	
	@PostMapping(path="/DeleteFloor")
	public @ResponseBody ResponseObj DeleteFloor(@RequestBody Floor f)
			throws NoSuchAlgorithmException, IOException {
		System.out.println(f.toString());
		StatusVersion s = mFloorRepository.getStatusVersion();
		int v = Integer.parseInt(s.getStatusVersionName())+1;
		mFloorRepository.UpdateStatusVersion(""+v);
		if(!f.getImagePlanPath().equals("building_temp.jpg")) {
			deleteimagefloor(f);
		}
		mFloorRepository.DeleteFloor(f.getFloorID());
		return new ResponseObj(200,"Success");
	}
	
	
}
