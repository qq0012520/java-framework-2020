package com.tudog.graphqldemo01.config;

import java.io.IOException;
import java.util.List;

import com.tudog.graphqldemo01.tools.ClassPathSchemaStringProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.kickstart.tools.boot.SchemaStringProvider;


@Configuration
public class GraphQLConfig {

    private static String schemaLocationPattern = "**/*.graphqls";

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchemaStringProvider mySchemaProvider(){
        return new SchemaStringProvider(){
            @Override
            public List<String> schemaStrings() throws IOException {
                SchemaStringProvider classpathSchemaProvider =
                     new ClassPathSchemaStringProvider(applicationContext,schemaLocationPattern);
                List<String> schemas = classpathSchemaProvider.schemaStrings();
                GraphQLSchemaMerger merger = new GraphQLSchemaMerger(schemas);
                String rootSchema = merger.makeRootSchema();
                schemas.add(rootSchema);
                return schemas;
            }
        };
    }
   
}