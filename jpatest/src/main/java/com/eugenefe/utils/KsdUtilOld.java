package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KsdUtilOld {
	private final static Logger logger = LoggerFactory.getLogger(KsdUtilOld.class);	
	private static String url = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	
	public static String callSeveltService(String referer, String payload){
		Document doc;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection  conn = (HttpURLConnection)obj.openConnection();
			
//			conn.addRequestProperty("User-Agent", "Mozilla");
//			conn.addRequestProperty("Accept-Language", "ko,en-US;q=0.8,en;q=0.6" );
//			conn.addRequestProperty("Content-Type", "application/xml");
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
	
	public static String searchProduct(String referer, String payload){
		Document doc;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection  conn = (HttpURLConnection)obj.openConnection();
			
//			conn.addRequestProperty("User-Agent", "Mozilla");
//			conn.addRequestProperty("Accept-Language", "ko,en-US;q=0.8,en;q=0.6" );
//			conn.addRequestProperty("Content-Type", "application/xml");
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
			logger.info("htm : {} ", html.toString());
			return html.toString();
			

		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
}
