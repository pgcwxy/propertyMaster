package com.fesco.propertyMaster.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/6/23.
 */
public class HttpClientUtils {
	public static String resolveResponse(HttpResponse response){
		HttpEntity entity = response.getEntity();
		StringBuilder result = new StringBuilder();
		if (entity != null) {
			InputStream instream = null;
			try {
				instream = entity.getContent();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String temp = "";
			try {
				while ((temp = br.readLine()) != null) {
					String str = new String(temp.getBytes(), "utf-8");
					result.append(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
}
