package com.blog.service.impl;

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

import com.blog.service.ImageService;
@Service
public class ImageServiceImpl implements ImageService {

	 @Override
	    public String uploadFile(String path, MultipartFile multipartFile) throws IOException {

	        //File name
	        String name = multipartFile.getOriginalFilename();

	        String randomId = UUID.randomUUID().toString();
	        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

	        //FullPath
	        String fullPath = path + File.separator + fileName;


	        //Crete folder if not created
	        File folder = new File(path);
	        if(!folder.exists()){
	            folder.mkdir();
	        }

	        //file copy
	        Files.copy(multipartFile.getInputStream(), Paths.get(fullPath));

	        return name;
	    }

	    @Override
	    public InputStream getResource(String path, String filename) throws FileNotFoundException {
	        String fullPath = path + File.separator + filename;
	        InputStream stream = new FileInputStream(fullPath);
	        return stream;
	    }


}
