package com.fesco.propertyMaster.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/6/13.
 */
public class PathUtils {
	public static String getJarPath(){
		URL url = PathUtils.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getPath(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0,filePath.lastIndexOf("/")+1);
		}
		File file = new File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}
	public static String getClassPathFilePath(String fileName){
		String a = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
		try {
			a = URLDecoder.decode(a,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		a = a.replace("file:/","");
		if (System.getProperty("file.separator").equals("\\")) {
			a = a.replaceAll("/","\\\\");
		}
		return a;
	}

	public static void main(String[] args) {
		String a = "/aaaaa/a";
		String separator = System.getProperty("file.separator");
		if (separator.equals("\\")) {
			a = a.replaceAll("/","\\\\");
		}
		System.out.println(a);
	}
}
