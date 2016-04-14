package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.eugenefe.enums.EKsdMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class KsdScrapUtilEnum {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KsdScrapUtilEnum.class);

	private static final String url = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";

	/**
	 * call getListJson(ksdMenu, data, "result", "value");
	 * 
	 */
	public static String getListJson(EKsdMenu ksdMenu, Map<String, String> data) {
		return getListJson(ksdMenu.getReferer(), ksdMenu.getPayload(),filterParameters(ksdMenu, data), "result", "value");
	}

	public static String getListJson(EKsdMenu ksdMenu, Map<String, String> data, String elementName, String attrName) {
		return getListJson(ksdMenu.getReferer(), ksdMenu.getPayload(), filterParameters(ksdMenu, data), elementName, attrName);
	}

	/**
	 * call getListJson(referer, payload, data, "result", "value")
	 * 
	 */
	public static String getListJson(String referer, String payload, Map<String, String> data) {
		return getListJson(referer, payload, data, "result", "value");
	}

	/**
	 * Main method for KsdScrapper !!
	 */
	public static String getListJson(String referer, String payload, Map<String, String> data, String elementName,String attrName) {
		List<Element> results = getDocument(referer, payload, data).select(elementName);
		StringBuffer strBuffer = new StringBuffer();

		if (results.size() == 0 || results.size() == 1 && results.get(0).children().size() == 0) {
			return null;
		}

		for (Map.Entry<String, String> entry : data.entrySet()) {
			strBuffer.append("\"").append(entry.getKey().toLowerCase()).append("\":\"").append(entry.getValue()).append("\",");
		}
		return buildJson(strBuffer.toString(), results, attrName);
	}

	public static  Document getDocument(String referer, String payload, Map<String, String> data) {
//		logger.info("payload : {},{}", referer, StrSubstitutor.replace(payload, data));
		return Jsoup.parse(callSeveltService(referer, StrSubstitutor.replace(payload, data)));
	}

	
	/*public static Map<EKsdMenu, String> getAllJson(Map<String, String> data) {
		Map<EKsdMenu, String> rst = new HashMap<EKsdMenu, String>();
		for (EKsdMenu ksdMenu : EKsdMenu.values()) {
			rst.put(ksdMenu, getListJson(ksdMenu, data));
		}
		return rst;
	}*/

	public static <E> List<E> convertTo(EKsdMenu ksdMenu, String jsonString) {
		try {
			Class clazz = Class.forName(ksdMenu.getTargetClassName());
			return convertTo(clazz, jsonString);
		} catch (ClassNotFoundException ex) {
			logger.error("ClasssNotFoundError for {}", ksdMenu);
		}
		return null;
	}

	public static <E> List<E> convertTo(EKsdMenu ksdMenu, Map<String, String> data) {
		String jsonString = getListJson(ksdMenu, filterParameters(ksdMenu, data));
//		String jsonString = getListJson(ksdMenu, data);
		return convertTo(ksdMenu, jsonString);
	}
	
	
	

	// *****************************private method********************************************************************
	private static <E> List<E> convertTo(Class<E> klass, String jsonString) {
		List<E> rst = new ArrayList<E>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	
	private static Map<String, String> filterParameters(EKsdMenu ksdMenu,  Map<String, String> rawData){
		Map<String, String> data = new HashMap<String, String>();
		for(String param: ksdMenu.getParameters()){
			data.put(param,  rawData.get(param));
		}
		return data;
	}
	
	
	private static String buildJson(String prefix, List<Element> elemenst, String attrName) {
		StringBuffer strBuffer = new StringBuffer();
		String tempStr;
		strBuffer.append("[");
		for (Element aa : elemenst) {
			strBuffer.append("{");
			if (prefix != null) {
				strBuffer.append(prefix);
			}
			for (Element bb : aa.children()) {
				strBuffer.append("\"").append(bb.tagName()).append("\":");
				tempStr = bb.attr(attrName);

				try {
					strBuffer.append(Integer.parseInt(tempStr));
				} catch (NumberFormatException nfe) {
					try {
						strBuffer.append(Double.parseDouble(tempStr));
					} catch (NumberFormatException nfee) {
						if (tempStr.equals("")) {
							tempStr = "null";
							strBuffer.append(tempStr);
						} else {
							strBuffer.append("\"").append(tempStr).append("\"");
						}

					}
				}

				strBuffer.append(",");
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("},");
		}

		if (strBuffer.lastIndexOf(",") > 0) {
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		}
		strBuffer.append("]");
		String rst = strBuffer.toString();
		return rst;
	}

	private static String callSeveltService(String referer, String payload) {
		Document doc;

		try {
//			URL obj = new URL(url);
			URL obj = new URL(EKsdMenu.getUrl());
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			// conn.addRequestProperty("User-Agent", "Mozilla");
			// conn.addRequestProperty("Accept-Language",
			// "ko,en-US;q=0.8,en;q=0.6" );
			// conn.addRequestProperty("Content-Type", "application/xml");
			conn.addRequestProperty("Referer", referer);
			// conn.addRequestProperty("Referer", "" );
			// logger.info("referer: {}", referer);

			conn.setDoOutput(true);
			conn.setDoInput(true);

			OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			w.write(payload);
			w.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer html = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine).append("\n");
			}

			in.close();
			conn.disconnect();
			return html.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
