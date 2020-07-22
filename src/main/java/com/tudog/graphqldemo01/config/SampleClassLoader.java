package com.tudog.graphqldemo01.config;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class SampleClassLoader extends ClassLoader {
    /* Call MyApp.main().
    //  */
    // public static void main(String[] args) throws Throwable {
    //     SampleClassLoader s = new SampleClassLoader();
    //     Class c = s.loadClass("MyApp");
    //     c.getDeclaredMethod("main", new Class[] { String[].class })
    //      .invoke(null, new Object[] { args });
    // }

    private ClassPool pool;

    public SampleClassLoader() {
        pool = new ClassPool();
    }

    /* Finds a specified class.
     * The bytecode for that class can be modified.
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        try {
            CtClass cc = pool.get(name);
            // modify the CtClass object here
            byte[] b = cc.toBytecode();
            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }
}