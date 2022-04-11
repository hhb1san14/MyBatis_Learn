package com.hhb.io;

import java.io.InputStream;

/**
 * @author hhb
 * @date 2022/4/11
 * @description
 */
public class Resource {
    // MyBatis 的第一步，加载制定目录下的配置文件，并将配置文件转换成InputStream
    public static InputStream getResourceAsInputStream(String path) {
        InputStream resourceAsStream = Resource.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

    public static void main(String[] args) {
        System.err.println(123);
    }
}
