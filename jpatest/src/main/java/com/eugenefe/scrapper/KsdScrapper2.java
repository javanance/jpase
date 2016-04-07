package com.eugenefe.scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.greyhawk.logger.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.eugenefe.entity.Ksd191T3;
import com.eugenefe.entity.Ksd200T1;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdDataTest;

public class KsdScrapper2 {
	private String payload;
	private String prePayload;
	private String referer;
	private static Properties properties = new Properties();
	private static final String url = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";

	public KsdScrapper2() {

	}

//	public Document getDocument(String isin) {
//		Map<String, String> data = new HashMap<String, String>();
//		 try {
//		 properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
//		 referer = properties.getProperty("[200_T1]referer");
//		 payload = properties.getProperty("[200_T1]payload");
//
//
//		data.put("ISIN", isin);
//
//		return Jsoup.parse(callSeveltService(referer, StrSubstitutor.replace(payload, data)));
//
//		 } catch (Exception ex) {
//		 return null;
//		 }
//	}
//	
	
	public Document getDocument(String menuId, Map<String, String> data) {
		 try {
		 properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
		 
		 referer = properties.getProperty("[" + menuId + "]referer");
		 payload = properties.getProperty("[" + menuId + "]payload");

		return Jsoup.parse(callSeveltService(referer, StrSubstitutor.replace(payload, data)));

		 } catch (Exception ex) {
		 return null;
		 }
	}
	
	public Document getDocument(String referer, String payload, Map<String, String> data) {
		return Jsoup.parse(callSeveltService(referer, StrSubstitutor.replace(payload, data)));
	}
	
	public Document getDocument(EKsdMenu menu,  Map<String, String> data) {
		return Jsoup.parse(callSeveltService(menu.getReferer(), StrSubstitutor.replace(menu.getPayload(), data)));
	}
	

	/*public String getListJson(String isin) {
		return getListJson(isin, "result", "value");
	}

	public String getListJson(String isin, String elementName, String attrName) {
		List<Element> results = getDocument(isin).select(elementName);

		StringBuffer strBuffer1 = new StringBuffer();
		
		strBuffer1.append("[");
		
		String tempStr ;
		for (Element aa : results) {
			strBuffer1.append("{");
			for(Element bb : aa.children()){
				strBuffer1.append("\"").append(bb.tagName()).append("\":");
				tempStr = bb.attr(attrName);
				System.out.println(bb.tagName()+"aa"+ tempStr );
			
				try {
					strBuffer1.append(Integer.parseInt(tempStr));
				} catch (NumberFormatException nfe) {
					try{
						strBuffer1.append(Double.parseDouble(tempStr));
					}
					catch(NumberFormatException nfee){
						if(tempStr.equals("")){
							tempStr="null";
							strBuffer1.append(tempStr);
						}else{
							strBuffer1.append("\"").append(tempStr).append("\"");
						}
						
					}
				}
				
				strBuffer1.append(",");
			}
			strBuffer1.deleteCharAt(strBuffer1.lastIndexOf(","));
			strBuffer1.append("},");
		}
		strBuffer1.deleteCharAt(strBuffer1.lastIndexOf(","));
		strBuffer1.append("]");
		String rst = strBuffer1.toString();
		
		return rst;
	}*/
	
//	public String getListJson(EKsdMenu menu, Map<String, String> data) {
//		return getListJson(menu,  data, "result", "value");
//	}
	
	public String getListJson(String menuId, Map<String, String> data) {
		return getListJson(menuId,  data, "result", "value");
	}
	
	public String getListJson(String menuId, Map<String, String> data, String elementName, String attrName) {
		List<Element> results = getDocument(menuId,  data).select(elementName);
		return buildJson(results, attrName);
	}
	
	
	private String buildJson(List<Element> elemenst, String attrName){
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append("[");
		
		String tempStr ;
		for (Element aa : elemenst) {
			strBuffer.append("{");
			for(Element bb : aa.children()){
				strBuffer.append("\"").append(bb.tagName()).append("\":");
				tempStr = bb.attr(attrName);
				System.out.println(bb.tagName()+"aa"+ tempStr );
			
				try {
					strBuffer.append(Integer.parseInt(tempStr));
				} catch (NumberFormatException nfe) {
					try{
						strBuffer.append(Double.parseDouble(tempStr));
					}
					catch(NumberFormatException nfee){
						if(tempStr.equals("")){
							tempStr="null";
							strBuffer.append(tempStr);
						}else{
							strBuffer.append("\"").append(tempStr).append("\"");
						}
						
					}
				}
				
				strBuffer.append(",");
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("},");
		}
		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		strBuffer.append("]");
		String rst = strBuffer.toString();
		
		return rst;
	}
	
	private  String callSeveltService(String referer, String payload){
		Document doc;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection  conn = (HttpURLConnection)obj.openConnection();
			
//			conn.addRequestProperty("User-Agent", "Mozilla");
//			conn.addRequestProperty("Accept-Language", "ko,en-US;q=0.8,en;q=0.6" );
//			conn.addRequestProperty("Content-Type", "application/xml");
//			TODO
			conn.addRequestProperty("Referer", referer );
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			
			OutputStreamWriter w  = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			w.write(payload);
			w.close();
			
//			logger.info("Request Url : {}, {}", url, payload);
			
			int status = conn.getResponseCode();
//			logger.info("Response : {}", status);
			
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer html = new StringBuffer();
			
			while ((inputLine = in.readLine())!= null){
				html.append(inputLine).append("\n");
//				logger.info("line : {} ", in.readLine().toString());
			}
			
			in.close();
			conn.disconnect();
//			logger.info("htm : {} ", html.toString());
			return html.toString();
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
}
