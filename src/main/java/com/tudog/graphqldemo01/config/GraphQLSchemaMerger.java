package com.tudog.graphqldemo01.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class GraphQLSchemaMerger {
    private static String QUERY_BLOCK_START_PART = "type Query{";
    
    private static String MUTATION_BLOCK_START_PART = "type Mutation{";

    private static String COMMON_BLOCK_END_PART = "\n}";
    
    /**
     * 匹配字符串中类似一下结果字符
     * "type someQuery"
     * "type otherQuery"
     */
    private static Pattern QUERY_NAME_PATTERN = Pattern.compile("type\\s+\\w+Query");


     /**
     * 匹配字符串中类似一下结果字符
     * "type someMutation"
     * "type otherMutation"
     */
    private static Pattern MUTATION_NAME_PATTER = Pattern.compile("type\\s+\\w+Mutation");;

    private static String COMMON_ENTRY_PARAMETER_MODEL = "(id: ID): ";

    private List<String> schemas;

    private List<String> queryEntryNames = new ArrayList<>();
    private List<String> mutationEntryNames = new ArrayList<>();

    private StringBuilder schemaBuilder = new StringBuilder();


    GraphQLSchemaMerger(List<String> schemas){
        this.schemas = schemas;
    }

    /**
     * 通过现有的schema来创建跟（root）schema，根schema主要包含Query和Mutation根元素
     */
    String makeRootSchema(){
        extractEntryNames();
        constructEntryBlock(QUERY_BLOCK_START_PART,COMMON_BLOCK_END_PART,queryEntryNames);
        schemaBuilder.append("\n\n");
        constructEntryBlock(MUTATION_BLOCK_START_PART,COMMON_BLOCK_END_PART,mutationEntryNames);
        return schemaBuilder.toString();
    }

    private void constructEntryBlock(String start,String end,List<String> items) {
        schemaBuilder.append(start);
        for (String queryEntryName : items) {
            schemaBuilder.append("\n    ");
            schemaBuilder.append(StringUtils.uncapitalize(queryEntryName));
            schemaBuilder.append(COMMON_ENTRY_PARAMETER_MODEL);
            schemaBuilder.append(queryEntryName + " ");
        }
        schemaBuilder.append(end);
    }

    private void extractEntryNames() {
        for (String schema : schemas) {
            Matcher queryMatcher = QUERY_NAME_PATTERN.matcher(schema);
            Matcher mutationMatcher = MUTATION_NAME_PATTER.matcher(schema);
            while(queryMatcher.find()){
                String queryEntryName = extractEntryName(queryMatcher.group(0));
                queryEntryNames.add(queryEntryName);
            }
            while(mutationMatcher.find()){
                String mutationEntryName = extractEntryName(mutationMatcher.group(0));
                mutationEntryNames.add(mutationEntryName);
            }
        }
    }

    private String extractEntryName(String compond){
        return compond.split("\\s+")[1];
    }
}