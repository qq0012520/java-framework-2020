package com.tudog.graphqldemo01.api.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.google.common.io.Files;
import com.tudog.graphqldemo01.model.common.UploadResult;
import com.tudog.graphqldemo01.tools.DateUtil;
import com.tudog.graphqldemo01.tools.HttpTool;
import com.tudog.graphqldemo01.tools.SignUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonMutation implements GraphQLMutationResolver {

    private String fileUploadRootPath;

    public CommonMutation() {
        try {
            fileUploadRootPath = ResourceUtils.getFile("classpath:").getPath() + File.separator + "static"
                    + File.separator + "upload";
        } catch (FileNotFoundException e) {
            log.error("File upload path init failed.");
        }
    }

    public UploadResult singleUpload(Part part,DataFetchingEnvironment env) {
        HttpServletRequest req = HttpTool.getHttpServletRequest(env);
        System.out.println(req.getAttribute("relationId"));
        String uploadPath = makeUploadPath();
        String id = SignUtil.fileNameByTime() + "." + Files.getFileExtension(part.getSubmittedFileName());
        String destFile = uploadPath + File.separator + id;
        log.info("Upload Part: {} to path {}", part.getSubmittedFileName(),destFile);
        try {
            part.write(destFile);
        } catch (IOException e) {
            log.error("Upload Part error : ", e.toString());
            id = "error";
        }
        return new UploadResult(id);
    }

    public List<UploadResult> multipleUpload(List<Part> parts,DataFetchingEnvironment env){
        List<UploadResult> results = new ArrayList<>();
        for (Part part : parts) {
            results.add(singleUpload(part, env));
        }
        return results;
    }

    /**
     * 生成文件上传路径，规则：根路径 + 当日日期(年月日)
     */
    private String makeUploadPath(){
        String curDayStr = LocalDate.now().format(DateUtil.FORMATTER_YYYYMMDD);
        File file = new File(fileUploadRootPath + File.separator + curDayStr + File.separator);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
