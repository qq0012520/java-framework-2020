package com.tudog.graphqldemo01.config.graphql;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Class Api 类加载器，用于将类字节码转换为Class对象
 */
class GraphApiClassLoader extends ClassLoader{

    private ClassPool pool;

    public GraphApiClassLoader(){
        pool = ClassPool.getDefault();
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            CtClass cc = pool.get(name);
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

    public Class<?> realDefineClass(String className,byte[] classBytes){
        return defineClass(className, classBytes, 0, classBytes.length);
    }
}