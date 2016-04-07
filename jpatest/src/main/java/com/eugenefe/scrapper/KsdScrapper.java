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

public class KsdScrapper {
	private static final String url = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KsdScrapper.class);

	public KsdScrapper() {
	}
	

	/**
	 * call getListJson(menu,  data, "result", "value");
	 * 
	 */
	public String getListJson(EKsdMenu menu,  Map<String, String> data) {
		return getListJson(menu.getReferer(), menu.getPayload(), data, "result", "value");
	}
	
	public String getListJson(EKsdMenu menu,  Map<String, String> data, String elementName, String attrName) {
		return getListJson(menu.getReferer(), menu.getPayload(), data, elementName, attrName);
	}
	
	/**
	 * call  getListJson(referer,  payload, data, "result", "value")
	 * 
	 */
	public String getListJson(String referer, String payload, Map<String, String> data) {
		return getListJson(referer,  payload, data, "result", "value");
	}
	/**
	 * Main method for KsdScrapper !!
	*/
	public String getListJson(String referer, String payload, Map<String, String> data, String elementName, String attrName) {
		List<Element> results = getDocument(referer, payload, data).select(elementName);
		if(results.size()==0 || results.size()==1 && results.get(0).children().size()==0) {
			return null;
		}
		StringBuffer strBuffer = new StringBuffer();
		for(Map.Entry<String , String> entry: data.entrySet()){
			strBuffer.append("\"").append(entry.getKey().toLowerCase()).append("\":\"").append(entry.getValue()).append("\",");
		}
		return buildJson(strBuffer.toString(),results, attrName);
	}
	
	public Document getDocument(String referer, String payload, Map<String, String> data) {
		return Jsoup.parse(callSeveltService(referer, StrSubstitutor.replace(payload, data)));
	}

//********************private method**********************************************8
	private String buildJson(String prefix, List<Element> elemenst, String attrName){
		StringBuffer strBuffer = new StringBuffer();
		String tempStr ;
		strBuffer.append("[");
		for (Element aa : elemenst) {
			strBuffer.append("{");
			if(prefix!=null){
				strBuffer.append(prefix);
			}
			for(Element bb : aa.children()){
				strBuffer.append("\"").append(bb.tagName()).append("\":");
				tempStr = bb.attr(attrName);
			
				try {
						strBuffer.append(Integer.parseInt(tempStr));
				} catch (NumberFormatException nfe) {
					try{
						strBuffer.append(Double.parseDouble(tempStr));
					}catch(NumberFormatException nfee){
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
		
		if(strBuffer.lastIndexOf(",")> 0){
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		}
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
			conn.addRequestProperty("Referer", referer );
//			conn.addRequestProperty("Referer", "" );
//			logger.info("referer: {}", referer);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			
			OutputStreamWriter w  = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			w.write(payload);
			w.close();
			
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer html = new StringBuffer();
			
			while ((inputLine = in.readLine())!= null){
				html.append(inputLine).append("\n");
			}
			
			in.close();
			conn.disconnect();
			return html.toString();
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
