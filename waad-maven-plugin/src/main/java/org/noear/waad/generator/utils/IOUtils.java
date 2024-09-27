package org.noear.waad.generator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;

public class IOUtils {

    public static void fileWrite(File file, String content) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(content);
        }finally {
            writer.close();
        }
    }

    public static void demo(){

    }

    //res::获取资源的RUL
    public static URL getResource(String name) {
        URL url = IOUtils.class.getResource(name);
        if (url == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
                url = loader.getResource(name);
            } else {
                url = ClassLoader.getSystemResource(name);
            }
        }

        return url;
    }
}
