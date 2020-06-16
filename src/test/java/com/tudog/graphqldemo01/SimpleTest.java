package com.tudog.graphqldemo01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class SimpleTest {
    

	Pattern pattern1 = Pattern.compile("type\\s+\\w+Query");

	Pattern pattern2 = Pattern.compile("type\\s+\\w+Mutation");


	private String str = "type fe33fefQueryw2QueryFEfwQuery  {}" + 
	"type  BookQuery{}" + 
	"type fewfwBookMutationewefweBookMutation {}";

    @Test
    public void simpleTest(){
        Matcher matcher = pattern2.matcher(str);
		while(matcher.find()){
            System.out.println("match: " + matcher.group(0));
        }
    }

    @Test
    public void simpleTest2(){
        String sss = "type BookQuery";
        System.out.println(sss.split("\\s+")[1]);
    }
}