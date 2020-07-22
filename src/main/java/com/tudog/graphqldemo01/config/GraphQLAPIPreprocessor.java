package com.tudog.graphqldemo01.config;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.scanners.AbstractScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
@Component
//ApplicationContextInitializedEvent
public class GraphQLAPIPreprocessor implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private class GraphQlApiClassCanner extends AbstractScanner {
        @SuppressWarnings({ "unchecked" })
        @Override
        public void scan(Object cls, Store store) {
            String className = getMetadataAdapter().getClassName(cls);
            put(store, className, className);
        }
    }

    @Bean
    BeanDefinitionRegistryPostProcessor graphApiBeanDefinitions() {
        return new BeanDefinitionRegistryPostProcessor() {

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }

            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

            }
        };
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // ConfigurableListableBeanFactory beanFactory =
        // ((ConfigurableApplicationContext) event.getApplicationContext())
        // .getBeanFactory();

        Reflections reflections = new Reflections("com.tudog.graphqldemo01.api", new GraphQlApiClassCanner());
        Set<String> graphApiClassNames = reflections.getStore().values(GraphQlApiClassCanner.class.getSimpleName());

        for (String graphApiClassName : graphApiClassNames) {
            Class<?> graphApiClass = modifyClass(graphApiClassName);
            // try {
            //     this.getClass().forName(graphApiClassName);
            //     graphApiClass.newInstance();
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
        }
       

        // Reflections reflections = new Reflections("com.tudog.graphqldemo01.api");
        // Set<Class<?>> graphApiTypes = new HashSet<>();
        // graphApiTypes.addAll(reflections.getSubTypesOf(GraphQLQueryResolver.class));
        // graphApiTypes.addAll(reflections.getSubTypesOf(GraphQLMutationResolver.class));
        // for (Class<?> apiType : graphApiTypes) {
        //     modifyClass(apiType);
        // }
    }

    private Class<?> modifyClass(String fullClassName) {
        try{
            String className = extractSimpleClassName(fullClassName);
            String classNameUncap = StringUtils.uncapitalize(className);
            ClassPool classPool = ClassPool.getDefault();
            CtClass resolverClass = classPool.makeClass(fullClassName);
            CtMethod newMethod = CtNewMethod.make("public " + fullClassName + " " + classNameUncap
                   + "(){" + "return this;" + "}", resolverClass);
            resolverClass.addMethod(newMethod);
            resolverClass.writeFile();
            //Class<?> target = resolverClass.toClass();
            log.info("Created boilerplate method : " + classNameUncap + " for class " + fullClassName);
            return null;
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