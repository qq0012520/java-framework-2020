package com.tudog.graphqldemo01.api.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import com.tudog.graphqldemo01.model.common.UploadResult;
import com.tudog.graphqldemo01.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

public class CommonMutation implements GraphQLMutationResolver {

    @Autowired
    private FileStorageService fileStorageService;

    public UploadResult singleUpload(Part part,DataFetchingEnvironment env) {
        String url = fileStorageService.saveFile(part);
        return new UploadResult(url);
    }

    public List<UploadResult> multipleUpload(List<Part> parts,DataFetchingEnvironment env){
        List<UploadResult> results = new ArrayList<>();
        for (Part part : parts) {
            results.add(singleUpload(part, env));
        }
        return results;
    }

}
