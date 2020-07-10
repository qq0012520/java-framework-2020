package com.tudog.graphqldemo01.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.tudog.graphqldemo01.tools.ClassPathSchemaStringProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.boot.SchemaStringProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GraphQLConfig {

    private static String schemaLocationPattern = "**/*.graphqls";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private List<GraphQLResolver<Void>> GraphResolvers;

    @Bean
    SchemaStringProvider mySchemaProvider() {
        log.info("GraphQL schemas processing...");
        printReflectiveMethods();
        return new SchemaStringProvider() {
            @Override
                public List<String> schemaStrings() throws IOException {
                    SchemaStringProvider classpathSchemaProvider = new ClassPathSchemaStringProvider(applicationContext,
                            schemaLocationPattern);
                    List<String> schemas = classpathSchemaProvider.schemaStrings();
                    GraphQLSchemaMerger merger = new GraphQLSchemaMerger(schemas);
                    String rootSchema = merger.makeRootSchema();
                    schemas.add(rootSchema);
                    return schemas;
                }
        };
    }

    private void printReflectiveMethods(){
        for (GraphQLResolver<Void> res : GraphResolvers) {
            String className = res.getClass().getSimpleName();
            if(className.contains("$")){ //过滤掉CGLIB增强类后缀
                className = className.substring(0,className.indexOf("$"));
            }
            String classNameUncap = StringUtils.uncapitalize(className);
            Method[] methods = res.getClass().getMethods();
            boolean hasReflectiveMethod = false;
            for (Method method : methods) {
                if(classNameUncap.equals(method.getName())){
                    hasReflectiveMethod = true;
                    log.info("Checked reflective method : " + method.toString());
                }
            }
            if(!hasReflectiveMethod){
                log.warn("Reflective method doesn't exists for " + res.getClass().getName());
            }
        }
    }
}