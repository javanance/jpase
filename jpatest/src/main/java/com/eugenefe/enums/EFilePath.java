package com.eugenefe.enums;

import java.util.Properties;

import com.eugenefe.utils.FilePathUtil;

public enum EFilePath {
	 KRX_DATA ("krxDataPath", "D:\\Dev\\krxData\\" , "KrxBaseParameter.json")
   , KSD_DATA ("ksdDataPath", "D:\\Dev\\ksdData\\" , "KsdBaseParameter.json")
   ;
	
	public String key;
	public String defaultDirectory;
	public String baseParam;
	
	private EFilePath ( String key, String defaultDirectory, String baseParam) {
		this.key = key;
		this.defaultDirectory =defaultDirectory;
		this.baseParam =baseParam ;
	}

	public String getKey() {
		return key;
	}

	public String getDefaultDirectory() {
		return defaultDirectory;
	}
	
	public String getBaseParamPath() {
		return getFilePath() + baseParam;
	}

	public String getFilePath(){
		try {
			Properties properties = new Properties();
			properties.load(EFilePath.class.getResourceAsStream("/filepath.properties"));
			return properties.getProperty(key);
		} catch (Exception e) {
			return defaultDirectory;
		}
	}
}
