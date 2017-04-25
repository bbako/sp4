package org.zerock.web;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    
    @GetMapping("/upload1")
    public void upload1(){
        
    }
    
    
    @GetMapping(name="/display", produces={"image/jpg"})
    @ResponseBody
    public byte[] display(String fileName)throws Exception{
    	InputStream fin= new FileInputStream("C:\\zzz\\upload\\"+fileName);
    	byte[] arr=IOUtils.toByteArray(fin);

    	return arr;
    	
    }
    
    @PostMapping("/upload2")
	@ResponseBody
	public String[] upload2Post(@RequestParam(value = "file[]") MultipartFile[] files) throws Exception {
		
		String[] arr = new String[files.length];
		
		for (int i = 0 ; i<files.length;i++) {
			UUID uid = UUID.randomUUID();
			
			String uidStr = uid.toString();
			String saveName = uidStr+"_"+files[i].getOriginalFilename();
			String thumbName = uidStr+"_s_"+files[i].getOriginalFilename();
			
			IOUtils.copy(files[i].getInputStream(), new FileOutputStream("C:\\zzz\\upload\\"+saveName));
			
			BufferedImage src = ImageIO.read(files[i].getInputStream());
			
			BufferedImage thumb = Scalr.resize(src, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_HEIGHT, 100);
			
			ImageIO.write(thumb, "jpg", new FileOutputStream("C:\\zzz\\upload\\"+thumbName));
			
			System.out.println(thumbName);
			arr[i] = thumbName;	
			
		}
				
		return arr;
	}
    @DeleteMapping("/delete")
	public String delete(@RequestBody String fileName){
		System.out.println("del"+fileName);
		return fileName;
	}
    
    @PostMapping(name= "/upload1", produces="text/plain;charset=UTF-8")   //지역번수의 이름으로 매칭을 시킨다, 응답이 나갈 mime타입을 지정
    @ResponseBody
    public String upload1Post(@RequestParam("file") MultipartFile file) throws FileNotFoundException, IOException{
        System.out.println("upload Post......");
        System.out.println( "getName: "+file.getName());
        System.out.println( "getOriginalFilename: "+file.getOriginalFilename());
        System.out.println( "size: "+file.getSize());
        
        
        UUID uid= UUID.randomUUID();
        String uidStr= uid.toString();
        String saveName= uidStr+"_"+file.getOriginalFilename();
        String thumbName= uidStr+"_s_"+file.getOriginalFilename();
        
        IOUtils.copy(file.getInputStream(), new FileOutputStream("c:\\zzz\\upload\\"+saveName));
        
        BufferedImage src= ImageIO.read(file.getInputStream());//메모리상 있는 이미지
        
        BufferedImage thumb = Scalr.resize(src, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_HEIGHT,100);
        ImageIO.write(thumb, "jpg", new FileOutputStream("c:\\zzz\\upload\\"+thumbName));
        
        return thumbName;
    }
    
}