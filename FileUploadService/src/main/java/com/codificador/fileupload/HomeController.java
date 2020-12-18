package com.codificador.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage() {
		return "home";
	}

	public String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadToResourceDir(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile)
			throws IllegalStateException, IOException {
		System.out.println("Uploading image to Resource Directory");
		String fileName = multipartFile.getOriginalFilename();
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File iconDir = new File(rootPath + File.separator + "icons");
		if (!iconDir.exists())
			iconDir.mkdirs();

		String filePath = iconDir.getAbsolutePath() + File.separator + fileName;
		System.out.println("Server Path : " + filePath);
		multipartFile.transferTo(new File(filePath));
		
		return new ModelAndView("success", "filename",getBaseURL(request)+"/icons/"+fileName);
	}

	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	public ModelAndView uploadAndGetImageAsByteArray(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile)
			throws IllegalStateException, IOException {
		System.out.println("Upload and get image as byte array");
		String rootPath = System.getProperty("catalina.home");
		File rootDir = new File(rootPath + File.separator + "uploads");
		if (!rootDir.exists())
			rootDir.mkdirs();

		long imageId = System.currentTimeMillis();
		File imageFile = new File(rootDir,imageId+"");
		multipartFile.transferTo(imageFile);
		return new ModelAndView("success", "filename", getBaseURL(request)+"/image/"+imageId);
	}
	
	
	@RequestMapping(value = "/upload2", method = RequestMethod.POST)
	public ModelAndView uploadAndGetAsBase64(HttpServletRequest request, @RequestParam("image") MultipartFile multipartFile)
			throws IllegalStateException, IOException {
		String rootPath = System.getProperty("catalina.home");
		File rootDir = new File(rootPath + File.separator + "uploads");
		if (!rootDir.exists())
			rootDir.mkdirs();

		long imageId = System.currentTimeMillis();
		File imageFile = new File(rootDir,imageId+"");
		multipartFile.transferTo(imageFile);
		
		return new ModelAndView("success", "filename", getImageBase64Encoded(imageId));
	}
	
	@RequestMapping(value = "/image/{imageId}")
	public @ResponseBody byte[] getImageByteArray(@PathVariable long imageId) throws IOException{
		System.out.println("Get image as Byte Array");
		String rootPath = System.getProperty("catalina.home");
		File rootDir = new File(rootPath + File.separator + "uploads");
		File imageFile = new File(rootDir,imageId+"");
		Path path = Paths.get(imageFile.getAbsolutePath());
		byte[] fileBytes = Files.readAllBytes(path);
		return fileBytes;
	}

	public String getImageBase64Encoded(long imageId) throws IOException{
		System.out.println("Get image as Base64 encoded string");
		String rootPath = System.getProperty("catalina.home");
		File rootDir = new File(rootPath + File.separator + "uploads");
		File imageFile = new File(rootDir,imageId+"");
		FileInputStream fis = new FileInputStream(imageFile);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		while( (fis.read(buffer)) != -1 ){
			bos.write(buffer);
		}
		byte[] fileBytes = bos.toByteArray();
		fis.close();
		bos.close();
		
		String imageContent = Base64.getEncoder().encodeToString(fileBytes);
		return imageContent;
	}
}