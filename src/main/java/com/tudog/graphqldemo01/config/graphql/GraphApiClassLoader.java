package com.tudog.graphqldemo01.config.graphql;

/**
 * Class Api 类加载器，用于将类字节码转换为Class对象
 */
class GraphApiClassLoader extends ClassLoader {

    public Class<?> realDefineClass(String className,byte[] classBytes){
        return defineClass(className, classBytes, 0, classBytes.length);
    }
}