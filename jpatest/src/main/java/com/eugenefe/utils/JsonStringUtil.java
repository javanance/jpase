package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.reflections.Reflections;

import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EKsdMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonStringUtil {
//	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JsonStringUtil.class);

	public static String extractElemntsFrom(String jsonString){
		StringBuffer strBuffer = new StringBuffer();
		String pureJson = getPureJson(jsonString);
		for (String aa : pureJson.split(",")) {
			strBuffer.append(aa.split("\"")[1]).append(",");
		}
		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
//		logger.info("columns : {},{}", strBuffer.toString());
		return strBuffer.toString();
	}
	
	public static String getPureJson(String jsonString) {
		String pureJson = "";
		if (jsonString.startsWith("[{") || jsonString.startsWith("{")) {
			pureJson = jsonString.split("\\{")[1];
			pureJson = pureJson.split("\\}")[0];
		} else {
			return jsonString;
		}
		return pureJson;
	}
}
