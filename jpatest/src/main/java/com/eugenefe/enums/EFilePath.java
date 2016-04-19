package com.eugenefe.enums;

import java.util.Properties;

import com.eugenefe.utils.FilePathUtil;

public enum EFilePath {
	 KRX_DATA ("krxDataPath", "D:\\Dev\\krxData\\")
   , KSD_DATA ("ksdDataPath", "D:\\Dev\\ksdData\\")
   ;
	
	public String key;
	public String defaultPath;
	
	private EFilePath ( String key, String defaultPath) {
		this.key = key;
		this.defaultPath =defaultPath;
	}

	public String getKey() {
		return key;
	}

	public String getDefaultPath() {
		return defaultPath;
	}
	
	public String getFilePath(){
		try {
			Properties properties = new Properties();
			properties.load(EFilePath.class.getResourceAsStream("/filepath.properties"));
			return properties.getProperty(key);
		} catch (Exception e) {
			return defaultPath;
		}
	}
}
