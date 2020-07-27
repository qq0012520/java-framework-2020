package com.tudog.graphqldemo01.config.graphql;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.tudog.graphqldemo01.tools.ClassPathSchemaStringProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.boot.SchemaStringProvider;
import graphql.schema.GraphQLScalarType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GraphQLConfig {

    private static String schemaLocationPattern = "**/*.graphqls";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private List<GraphQLResolver<Void>> graphResolvers;
    
    /**
     * 像容器中注入空GraphQL解析器实现
     * 目的是是因为我们的GraphQL自定义解析器都是
     * 通过 BeanDefinitionRegistryPostProcessor 手动注入的
     * 由于框架代码使用了 @ConditionalOnBean({GraphQLResolver.class}) 注解，
     * 这个注解会先于我们注入自己的解析器之前判断
     * 所有但是通过@Bean注入容器的话就可以解决这个问题
     */
    @Bean
    GraphQLResolver<Void> PlaceHolderResolver(){
        return new PlaceHolderResolver();
    }

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

    @Bean
    GraphQLScalarType uploadScalarType() {
        return ApolloScalars.Upload;
    }

    private void printReflectiveMethods(){
        for (GraphQLResolver<Void> res : graphResolvers) {
            String className = res.getClass().getSimpleName();
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