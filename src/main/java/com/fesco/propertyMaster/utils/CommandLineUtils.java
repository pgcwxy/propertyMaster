package com.fesco.propertyMaster.utils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/13.
 */
public class CommandLineUtils {
	public static String executeCommond(String cmd) {
		String ret = "";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			/*InputStreamReader ins = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ins);
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
				ret += line + "<br>";
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		System.out.println(executeCommond("ipconfig"));
	}
}
