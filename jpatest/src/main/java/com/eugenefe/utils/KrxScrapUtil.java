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

public class KrxScrapUtil {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KrxScrapUtil.class);

	public static String getListJson(EKrxMenuDyn krxMenu) {
		int timeout = 1000000;
		StringBuffer strBuffer = new StringBuffer();

		for (String para : krxMenu.getParameters()) {
			
			strBuffer.append("\"").append(para.toLowerCase()).append("\":\"").append(krxMenu.getFormData().get(para)).append("\",");
		}
//		logger.info("strBuffer: {},{}", strBuffer.toString());
		return buildJson(strBuffer.toString(), scrap(krxMenu.getUrl(), krxMenu.getOptUrl(), krxMenu.getElement(), krxMenu.getFormData(), timeout));
	}

	
	public static <E> List<E> convertTo(EKrxMenuDyn krxMenu) {
		return convertTo(krxMenu, getListJson(krxMenu));
	}

	public static <E> List<E> convertTo(EKrxMenuDyn krxMenu, String jsonString) {
		try {
			Class klass = Class.forName(krxMenu.getTargetClassName());
			return JsonStringUtil.convertTo(klass, jsonString);
		} catch (ClassNotFoundException ex) {
			logger.error("ClasssNotFoundError for {}", krxMenu);
		}
		return null;
	}

	
	
	
	

	// *****************************private method********************************************************************
	
	
	private static String buildJson(String prefix,  String jsonString) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

//		logger.info("split : {}", jsonString.split("\\{")[1]);
		
		for (String aa : jsonString.split("\\{")) {
			if(!aa.equals("[")){
				strBuffer.append("{");
				if (prefix != null) {
					strBuffer.append(prefix);
				}
				strBuffer.append(aa);
			}
		}
		String rst = strBuffer.toString();
		return rst;
	}
	
	public static String scrap(String url, String optUrl, String element,  Map<String, String> formData, int timeout){
		String rst =null;
		try {
			Document optDoc = Jsoup.connect(optUrl).timeout(timeout).get();
			String optCode = optDoc.select("body").text();
			formData.put("code", optCode);

//			logger.info("optCod : {},{}", optCode , formData);

			Document rstDoc = Jsoup.connect(url)
					 			   .timeout(timeout)
					 			   .data(formData)
					 			   .post();
			
			rst = rstDoc.select("body").text();
//			logger.info("rst :{},{}", rst.split(element+ "\"\\s*:")[1]);
			return rst.split(element+ "\"\\s*:")[1];
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	

}
