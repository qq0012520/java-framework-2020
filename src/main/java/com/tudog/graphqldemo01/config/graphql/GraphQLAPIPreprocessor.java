package com.tudog.graphqldemo01.config.graphql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.scanners.AbstractScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import lombok.extern.slf4j.Slf4j;

/**
 * GraphQL Api 类预处理器，给Api类添加一些通用样板方法
 */
@Slf4j
@Configuration
public class GraphQLAPIPreprocessor{
    /**
     * GraphQL api 根路径
     */
    public static final String GRAPHQL_API_RESOLVER_ROOT_PACKAGE = "com.tudog.graphqldemo01.api";

    @Bean
    BeanDefinitionRegistryPostProcessor graphAPIBeanRegister(){
        return new BeanDefinitionRegistryPostProcessor(){
			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				
			}
			@Override
			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
                List<Class<?>> graphApiClasses = equipGraphApiClasses();
                for (Class<?> apiClass : graphApiClasses) {
                    BeanDefinition beanDefinition = BeanDefinitionBuilder
                        .genericBeanDefinition(apiClass)
                        .getBeanDefinition();
                    String beanSimpleName = StringUtils.uncapitalize(apiClass.getSimpleName());
                    System.out.println(beanDefinition);
                    registry.registerBeanDefinition(beanSimpleName, beanDefinition);
                }
            }
        };
    }

    @SuppressWarnings({ "unchecked" })
    private class GraphQlApiClassCanner extends AbstractScanner {
        @Override
        public void scan(Object cls, Store store) {
            String className = getMetadataAdapter().getClassName(cls);
            put(store, className, className);
        }
    }

    private List<Class<?>> equipGraphApiClasses(){
        Reflections reflections = new Reflections(GRAPHQL_API_RESOLVER_ROOT_PACKAGE,new GraphQlApiClassCanner());
        Set<String> graphApiClassNames = reflections.getStore()
            .values(GraphQlApiClassCanner.class.getSimpleName());
        if(graphApiClassNames.size() == 0){
            log.error("Can't find any resolver classes in package: " + GRAPHQL_API_RESOLVER_ROOT_PACKAGE);
            return Collections.emptyList();
        }
        List<Class<?>> graphApiClasses = new ArrayList<>();
        for (String graphApiClassName : graphApiClassNames) {
            graphApiClasses.add(modifyClass(graphApiClassName));
        }
        return graphApiClasses;
    }

    private Class<?> modifyClass(String fullClassName) {
        try{
            String className = extractSimpleClassName(fullClassName);
            String classNameUncap = StringUtils.uncapitalize(className);
            ClassPool classPool = ClassPool.getDefault();
            CtClass resolverClass = classPool.get(fullClassName);
            CtMethod newMethod = CtNewMethod.make("public " + fullClassName + " " + classNameUncap
                    + "(){" + "return this;" + "}", resolverClass);
            resolverClass.addMethod(newMethod);
            Class<?> finalClass = resolverClass.toClass();
            return finalClass;
        }catch(Exception e){
            log.error("Error with creating boilerplate methods. Error message : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private String extractSimpleClassName(String fullClassName){
        String lastStr = StringUtils.getFilenameExtension(fullClassName);
        return lastStr;
    }

}