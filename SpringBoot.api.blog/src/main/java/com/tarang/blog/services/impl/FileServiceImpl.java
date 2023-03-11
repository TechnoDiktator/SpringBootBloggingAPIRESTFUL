package com.tarang.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tarang.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	
	//This function will help us upload an image file from the client to a folder in our server
	//what we are returning from this function is the path of the file non the clients computer
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//path - our server folder where we want to put the file
		//file - file that is present on the clients pc.
		
		//file name
		String name  =  file.getOriginalFilename();
		
		//will give a random name to our image
		String randomID = UUID.randomUUID().toString();
		String fileNmae1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		
		//full path on our server where we want to upload
		String filePath = path + File.separator + fileNmae1;
		
		//create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
			
		}
		
		//file copy
		Files.copy(file.getInputStream() , Paths.get(filePath));
		return fileNmae1;
	}

	
	
	//this function will get us the data of the file in the form of an input stream from clients computer
	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		
		//making the full path
		String fullPath  =  path + File.separator + filename;
		
		InputStream is  = new FileInputStream(fullPath);
		return is;
		
		//now in this function we are retreiving the file from the specified path 
		//our file is not present in the DB only its path is present
		
	}

	
	
	
}
