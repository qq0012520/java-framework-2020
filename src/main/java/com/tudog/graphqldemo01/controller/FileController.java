package com.tudog.graphqldemo01.controller;

import com.tudog.graphqldemo01.exception.StorageFileNotFoundException;
import com.tudog.graphqldemo01.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> getFileDownloads(String filename){
        Resource file = fileStorageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    // @GetMapping(value = "/image")
    // public @ResponseBody byte[] getFile(String filename)  {
    //     org.springframework.http.MediaType
    //     Resource file = fileStorageService.loadAsResource(filename);
    //     try{
    //         InputStream in = file.getInputStream();
    //         return IOUtils.toByteArray(in);
    //     }catch(Exception e){
    //         return null;
    //     }
    // }

    
    @ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
    }
}