package com.tudog.graphqldemo01;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class SimpleTest {

    Pattern pattern1 = Pattern.compile("type\\s+\\w+Query");

    Pattern pattern2 = Pattern.compile("type\\s+\\w+Mutation");

    private String str = "type fe33fefQueryw2QueryFEfwQuery  {}" + "type  BookQuery{}"
            + "type fewfwBookMutationewefweBookMutation {}";

    public void simpleTest() {
        Matcher matcher = pattern2.matcher(str);
        while (matcher.find()) {
            System.out.println("match: " + matcher.group(0));
        }
    }

    public void simpleTest2() {
        String sss = "type BookQuery";
        System.out.println(sss.split("\\s+")[1]);
    }

    public void simple3() throws NotFoundException, IOException, CannotCompileException {
        CtClass point = ClassPool.getDefault().get("com.tudog.graphqldemo01.api.book.BookQuery");
        CtMethod m = CtNewMethod.make(
                        "public void xmove(int dx) { System.out.println(\"fqefqwwfqw\"); }",
                        point);
        point.addMethod(m);
        point.writeFile("d:/");
    }

    @Test
    public void simple4(){
        System.out.println(Integer.parseInt("005"));
    }

}