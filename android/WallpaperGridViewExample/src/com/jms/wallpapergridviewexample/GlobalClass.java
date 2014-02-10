package com.jms.wallpapergridviewexample;

public class GlobalClass {
	private	static GlobalClass instance;
	
	public static GlobalClass instance() {
		if(instance == null) {
			instance = new GlobalClass();
		}
		
		return instance;
	}
}
