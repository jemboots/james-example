package com.jms.wallpapergridviewexample;

import java.io.File;

import android.content.Context;
import android.os.Environment;


public class GlobalClass {
	private	static GlobalClass instance;
	private static final String applicationCacheFolder = "wallpaper_jms/cache/";
	private static final String applicationPicFolder = "wallpaper_jms/data/";
	
	public static GlobalClass instance() {
		if(instance == null) {
			instance = new GlobalClass();
		}
		
		return instance;
	}
	
	public File getCacheFolder(Context context) {
		File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), applicationCacheFolder);
            if(!cacheDir.isDirectory()) {
            	cacheDir.mkdirs();
            }
        }
        
        if(!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir(); //get system cache folder
        }
        
		return cacheDir;
	}
	
	public File getDataFolder(Context context) {
		File dataDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        	dataDir = new File(Environment.getExternalStorageDirectory(), applicationPicFolder);
            if(!dataDir.isDirectory()) {
            	dataDir.mkdirs();
            }
        }
        
        if(!dataDir.isDirectory()) {
        	dataDir = context.getFilesDir();
        }
        
		return dataDir;
	}
}
