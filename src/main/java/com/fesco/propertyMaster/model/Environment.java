package com.fesco.propertyMaster.model;

/**
 * Created by Administrator on 2017/6/13.
 */
public enum Environment {
	dev(1),test(2),uat(3),pro(4);
	private int sortLevel;

	Environment(int sortLevel) {
		this.sortLevel = sortLevel;
	}

	public int getSortLevel() {
		return sortLevel;
	}

	public static void main(String[] args) {
		System.out.println(Environment.dev);
	}
}
