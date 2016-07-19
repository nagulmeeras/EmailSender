package com.pramati.emailsender.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.cglib.core.GeneratorStrategy;

public class PropertiesUtil {
	private static Properties properties;
	public PropertiesUtil() throws IOException{
		loadPropertyFile();
	}
	public static void loadPropertyFile() throws IOException{
		String fileName = "dl.properties";
		
		File file = new File(fileName);
		
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			properties = new Properties();
			properties.load(fis);
			fis.close();
		}else{
			System.out.println("propertie not found");
		}
	}
	public static String getPropertyValue(String key){
		String value = properties.getProperty(key);
		return value;
	}
}
