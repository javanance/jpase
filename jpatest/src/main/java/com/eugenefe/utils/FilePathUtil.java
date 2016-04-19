package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Timeout;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.eugenefe.enums.EKrxMenuDyn;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.enums.EKsdMenuDyn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class FilePathUtil {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FilePathUtil.class);
	private static Properties properties = new Properties();
	
	public static String getFilePath(String menu){
		try {
			properties.load(FilePathUtil.class.getResourceAsStream("/filepath.properties"));
			return properties.getProperty(menu);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	

}
