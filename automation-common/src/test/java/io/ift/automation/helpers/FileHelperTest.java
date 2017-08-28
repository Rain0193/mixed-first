package io.ift.automation.helpers;


import org.testng.annotations.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FileHelperTest {

    @Test
    public void testReadFile(){
//        String vuePath= "simple-report/vue.min.js";
//        String vue= FileHelper.readFileToString("simple-report/vue.min.js");
//        String path = FileHelper.createFileInClassPath("simple-report/","vue.min.js").getAbsolutePath();
//        FileHelper.writeToFile(path,vue);
//        System.out.println(vue);
    }

    @Test
    public void testCreateDir(){
        String path = FileHelper.getSrcPath();
        FileHelper.createDir(path,"abc/efg");
        FileHelper.deleteFile(path + "\\abc\\efg");
        FileHelper.deleteFile(path+"/abc\\");
    }

    @Test
    public void classPath_test() throws UnsupportedEncodingException {
        String path = FileHelper.getSrcPath()+File.separator+"中文命名";
        FileHelper.writeToFile(path,"test");
        System.out.println("testing");
        String configPath="D:/%e9%a1%b9%e7%9b%ae/%e6%b5%8b%e8%af%95%e7%9b%b8%e5%85%b3/apitest1/src/test/resources/servicedescription\\waiwang\\waiwang.json";
        System.out.println(URLDecoder.decode(configPath, "utf-8"));
        System.out.println(URLDecoder.decode(configPath, "utf-8"));

    }

}
