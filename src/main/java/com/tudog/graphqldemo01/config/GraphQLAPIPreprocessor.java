package com.tudog.graphqldemo01.config;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.ClassFile;
import lombok.extern.slf4j.Slf4j;

/**
 * GraphQL Api 类预处理器，给Api类添加一些通用样板方法
 */
@Slf4j
@Component
public class GraphQLAPIPreprocessor implements ApplicationListener<ApplicationContextInitializedEvent > {
        
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        String classFilePath = getClass().getResource("/").getPath();
        String[] classSuffixStrings = aquireBeanClassSuffixes();
        IOFileFilter[] suffixFilters = stringsToSuffixFilters(classSuffixStrings);
        Collection<File> classFiles = FileUtils.listFiles(new File(classFilePath),
            FileFilterUtils.or(suffixFilters)
            , FileFilterUtils.trueFileFilter());
    
        for (File file : classFiles) {
            String classFileName = file.getName();
            String className = classFileName.substring(0,classFileName.indexOf("."));
            String classNameUncap = StringUtils.uncapitalize(className);
            modifyClass(file, classNameUncap);
        }
    }

    private void modifyClass(File file, String classNameUncap) {
        try(DataInputStream classInput = new DataInputStream(
            new BufferedInputStream(new FileInputStream(file)))) {
            ClassFile classFile = new ClassFile(classInput);
            String fullClassName = classFile.getName();
            ClassPool classPool = ClassPool.getDefault();
            CtClass resolverClass = classPool.get(fullClassName);
            CtMethod m = CtNewMethod.make("public " + fullClassName + " " + classNameUncap
                   + "(Integer id){" + "return this;" + "}", resolverClass);
            resolverClass.addMethod(m);
            resolverClass.toClass();
        } catch (Exception e) {
            log.error("Error with creating boilerplate methods. Error message : " + e.getMessage());
            System.exit(1);
        }
    }

    private IOFileFilter[] stringsToSuffixFilters(String[] strings){
        IOFileFilter[] filters = new IOFileFilter[strings.length];
        for (int i = 0; i < filters.length; i++) {
            filters[i] = FileFilterUtils.suffixFileFilter(strings[i]);
        }
        return filters;
    }

    private String[] aquireBeanClassSuffixes() {
        List<String> resultList = new ArrayList<>();
        Set<GraphQLProcessorSuffix> processorSuffixes = EnumSet.allOf(GraphQLProcessorSuffix.class);
        for (GraphQLProcessorSuffix processorSuffix : processorSuffixes) {
            resultList.add(processorSuffix.getName() + ".class");
        }
        return resultList.toArray(new String[0]);
    }

}