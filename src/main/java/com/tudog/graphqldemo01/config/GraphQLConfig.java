package com.tudog.graphqldemo01.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.tudog.graphqldemo01.tools.ClassPathSchemaStringProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.boot.SchemaStringProvider;

@Configuration
public class GraphQLConfig {

    private static String schemaLocationPattern = "**/*.graphqls";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private List<GraphQLQueryResolver> queryResolvers;

    @Bean
    public SchemaStringProvider mySchemaProvider() {
        System.out.println("=====================mySchemaProvider================");
        printClassNames();
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

    private void printClassNames(){
        for (GraphQLQueryResolver res : queryResolvers) {
            Method[] methods = res.getClass().getMethods();
            for (Method method : methods) {
                System.out.println(method);
            }
        }
    }
}