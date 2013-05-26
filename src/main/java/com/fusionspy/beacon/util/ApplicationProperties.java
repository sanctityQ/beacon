package com.fusionspy.beacon.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

/**
 * ApplicationProperties
 * User: qc
 * Date: 11-10-24
 * Time: 下午4:50
 */
public class ApplicationProperties {

    private static final String resources = "application.properties";

    private Properties properties;

     public Properties init(){
         try {
             this.properties = PropertiesLoaderUtils.loadAllProperties(resources);
             return this.properties;
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }

    public void write(Map<String,String> map) {

           try {
               for(String key : map.keySet()) {
                   System.out.println(key + " = " + map.get(key));
                   this.properties.setProperty(key, map.get(key));
               }
               FileOutputStream fileOutputStream = new FileOutputStream(
                   new File(ApplicationProperties.class.getClassLoader().getResource(resources).toURI()));
               this.properties.store(fileOutputStream, "adasd");
               System.out.println("write end");
               fileOutputStream.close();
           } catch (FileNotFoundException e) {

           } catch (URISyntaxException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
    }


    public static void main(String[] args){
        try {
           System.out.println(new File(ApplicationProperties.class.getClassLoader().getResource(resources).toURI()).toString()) ;
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
