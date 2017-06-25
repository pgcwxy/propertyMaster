package com.fesco.propertyMaster.utils;

import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * 控制台交互工具类
 * @date 10:26 2017/6/13
 * @author 王新宇
 */
public class ConsoleUtils {
	private static Logger logger = Logger.getLogger(ConsoleUtils.class);
	/**
	 * 显示一句话，然后等待用户输入，返回用户输入的值
	 * @author 王新宇
	 * @Date 10:27 2017/6/13
	 * @param prompt
	 * @return java.lang.String
	 */
	public static String readDataFromConsole(String prompt) {
		Scanner scanner = new Scanner(System.in);
		System.out.print(prompt+"：");
		String userInput = scanner.nextLine();
		logger.info("提示用户输入内容为："+prompt+"  "+"用户输入内容为："+userInput);
		return userInput;
	}
	public static void println(String inputString){
		logger.info("控制台输出："+inputString);
		System.out.println(inputString);
	}
	public static void print(String inputString){
		logger.info("控制台输出："+inputString);
		System.out.println(inputString);
	}
}
