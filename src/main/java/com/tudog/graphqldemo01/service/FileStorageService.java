package com.tudog.graphqldemo01.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.servlet.http.Part;

import com.google.common.io.Files;
import com.tudog.graphqldemo01.exception.StorageFileNotFoundException;
import com.tudog.graphqldemo01.tools.DateUtil;
import com.tudog.graphqldemo01.tools.SignUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 文件存储业务类
 */
@Slf4j
@Service
public class FileStorageService {
    private File fileUploadPath;

    public FileStorageService(@Value("${web.upload.path}") String uploadPath){
        if(!StringUtils.isEmpty(uploadPath)){
            fileUploadPath = Paths.get(uploadPath).toFile();
            if(!fileUploadPath.exists()){
                log.error("Not found upload path: " + fileUploadPath);
                System.exit(0);
            }
        }else{
            log.error("Property: ${web.upload.path} can't be null.");
            System.exit(0);

        }
    }

    public String saveFile(Part part){
        String dateDir = generateTimePath();
        String uploadPath = makeUploadPath(dateDir);
        String id = SignUtil.fileNameByTime() + "." + Files.getFileExtension(part.getSubmittedFileName());
        String destFile = uploadPath + File.separator + id;
        log.info("Upload Part: {} to path {}", part.getSubmittedFileName(),destFile);
        try {
            part.write(destFile);
        } catch (IOException e) {
            log.error("Upload Part error : ", e.toString());
            id = "error";
        }
        return dateDir + id;
    }

	public Path load(String filename) {
		return fileUploadPath.toPath().resolve(filename);
	}

    public Resource loadAsResource(String filename) {
        try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else { 
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
    }

    /**
     * 生成文件上传路径，规则：根路径 + 当日日期(年月日)
     */
    private String makeUploadPath(String dateDir){
        File file = new File(fileUploadPath + File.separator + dateDir);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private String generateTimePath(){
        String curDayStr = LocalDate.now().format(DateUtil.FORMATTER_YYYYMMDD);
        return curDayStr + File.separator;
    }
}