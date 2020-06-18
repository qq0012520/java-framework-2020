package com.tudog.graphqldemo01.config;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.ClassFile;

@Component
public class ApiBeanRegistry implements ApplicationListener<ApplicationContextInitializedEvent > {

    @Override
    public void onApplicationEvent(final ApplicationContextInitializedEvent  event) {
        String classFilePath = getClass().getResource("/").getPath();
        Collection<File> classFiles = FileUtils.listFiles(new File(classFilePath),
                FileFilterUtils.suffixFileFilter("Query.class"), FileFilterUtils.trueFileFilter());
    
        for (File file : classFiles) {
            String classFileName = file.getName();
            String className = classFileName.substring(0,classFileName.indexOf("."));
            String classNameUncap = StringUtils.uncapitalize(className);
            try(DataInputStream classInput = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                ClassFile classFile = new ClassFile(classInput);
                String fullClassName = classFile.getName();
                ClassPool classPool = ClassPool.getDefault();
                CtClass resolverClass = classPool.get(fullClassName);
                CtMethod m = CtNewMethod.make("public " + fullClassName + " " + classNameUncap
                       + "(Integer id){" + "return this;" + "}", resolverClass);
                resolverClass.addMethod(m);
                resolverClass.toClass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}