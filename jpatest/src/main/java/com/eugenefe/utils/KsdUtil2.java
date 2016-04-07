package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.enums.EKsdDataItem;
import com.eugenefe.enums.EKsdElsDataGroup;
import com.eugenefe.model.ElsHeader;


public class KsdUtil2 {
	private final static Logger logger = LoggerFactory.getLogger(KsdUtil2.class);	
	private static String url = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	
	private static String elsUrl = "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	
//	private static String _action = "<reqParam action=\"";
//	private static String _task = "\" task=\"";
//	private static String _menuNo = "\"><MENU_NO value=\"";
//	private static String _cmm_btn = "\"/><CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,";
//	private static String _xpath = "\"/><W2XPATH value=\"";
//	private static String _isin = "\"/><ISIN value=\"";
	
	private static String _issuerCode = "\"/><ISSUCO_CUSTNO value=\"";
	private static String _prodType = "\"/><SECN_TPCD value=\"";	
	private static String _endArg ="\"/></reqParam>";
	
	private static String _elsPayLoad = "<reqParam action=\"%s\" task=\"%s\"><MENU_NO value=\"%s\"/><CMM_BTN_ABBR_NM value=\"%s\"/><W2XPATH value=\"%s\"/><ISIN value=\"%s\"/></reqParam>";
	
	
	private static String elsCmmBtn ="allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,";
	private static String elsReferer = "http://www.seibro.or.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200";
	private static String elsTask = "ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask";
	private static String elsMenuNo = "200";
	private static String elsXpath ="/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml";
	
	
	
	public static Document getElsDataDoc(EKsdElsDataGroup dataType, String isin){
		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, payload), "",Parser.xmlParser());
		
//		logger.info("DOC: {}", doc);
		return doc;
	}
	
	public static Map<ElsHeader, String> getElsDataMap(EKsdElsDataGroup dataType, String isin){
		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, payload), "",Parser.xmlParser());
		
		Map<ElsHeader, String> rstMap = new HashMap<ElsHeader, String>();
		
		switch (dataType) {
		case BASIC:
			rstMap =getElsSingleDataMap(doc, isin);
			break;

		default:
			rstMap = getElsListDataMap(doc, isin);
			break;
		}
		
		
//		logger.info("DOC: {},{}", rstMap);
		return rstMap;
	}
	
//----------- specific Method ------------------------------
	public static Document getElsInfo(String isin){
		String issueInfoAction = "issuInfoList";
		String issueInfoPayload =String.format(_elsPayLoad, issueInfoAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		
		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, issueInfoPayload), "",Parser.xmlParser());
		
		return doc;
	}
	
	public static Document getElsStrikePrice(String isin){
		String exerciseAction = "bassetXrcList";
		String exerPayload =String.format(_elsPayLoad, exerciseAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);

		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, exerPayload),"", Parser.xmlParser());
		return doc;
	}
	
	public static Document getElsSchedule(String isin){
		String scheduleAction = "midValatSkedulRedCondiList";
		String schedulePayload =String.format(_elsPayLoad, scheduleAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, schedulePayload),"", Parser.xmlParser());
//		logger.info("Doc : {}", doc);
		return doc;
	}
	
	public static Document getElsBaseInfo(String isin){
		String baseInfoAction = "bassetInfoList";
		String baseInfoPayload =String.format(_elsPayLoad, baseInfoAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		Document doc = Jsoup.parse(KsdUtil2.callSeveltService(elsReferer, baseInfoPayload),"", Parser.xmlParser());
		logger.info("Doc : {}", doc);
		return doc;
	}
	
	
	public static Map<String, String> getElsInfoMap(String isin){
		Document doc = getElsInfo(isin);

		Map<String, String> rstMap = new HashMap<String, String>();
		logger.info("xml : {}", doc.toString());
		
		for(Element e : doc.select("result").first().children()){
			logger.info("aaa:{}, {}", e.tagName(), e.attr("value"));
		}
		
		return rstMap;
	}
	
	
	
	public static Map<String, String> getElsStrikePriceMap(String isin){
		Document doc = getElsStrikePrice(isin);

		Map<String, String> rstMap = new HashMap<String, String>();
		logger.info("xml : {}", doc.toString());
		int cnt =0;
		StringBuffer rst= new StringBuffer();
		for(Element e : doc.select("data>result")){
//		for(Element e : doc.select("data")){	
			cnt = cnt +1;
//			logger.info("aaa:{}, {}", cnt, e.toString());
			for( Element bb : e.children()){
				rst.append(isin).append(";")
					.append(cnt).append(";")
					.append(bb.tagName()).append(";")
				   .append(bb.attr("value")).append("\n");
				logger.info("bbb:{}, {}", bb.tagName(), bb.attr("value"));
			}
			
		}
		logger.info("ccc:{}, {}", rst.toString());
		return rstMap;
	}
	
	
	
	public static Map<String, String> getElsScheduleMap(String isin){
		Document doc = getElsSchedule(isin);

		Map<String, String> rstMap = new HashMap<String, String>();
//		logger.info("xml : {}", doc.toString());
		int cnt =0;
		StringBuffer rst= new StringBuffer();
		for(Element e : doc.select("data>result")){
//		for(Element e : doc.select("data")){	
			cnt = cnt +1;
//			logger.info("aaa:{}, {}", cnt, e.toString());
			for( Element bb : e.children()){
				rst.append(isin).append(";")
					.append(cnt).append(";")
					.append(bb.tagName()).append(";")
				   .append(bb.attr("value")).append("\n");
//				logger.info("bbb:{}, {}", bb.tagName(), bb.attr("value"));
			}
			
		}
		logger.info("ccc:{}, {}", rst.toString());
		return rstMap;
	}
	
	
	
	public static Map<String, String> getElsBaseInfoMap(String isin){
		Document doc = getElsBaseInfo(isin);

		Map<String, String> rstMap = new HashMap<String, String>();
//		logger.info("xml : {}", doc.toString());
		int cnt =0;
		StringBuffer rst= new StringBuffer();
		for(Element e : doc.select("data>result")){
//		for(Element e : doc.select("data")){	
			cnt = cnt +1;
//			logger.info("aaa:{}, {}", cnt, e.toString());
			for( Element bb : e.children()){
				rst.append(isin).append(";")
					.append(cnt).append(";")
					.append(bb.tagName()).append(";")
				   .append(bb.attr("value")).append("\n");
//				logger.info("bbb:{}, {}", bb.tagName(), bb.attr("value"));
			}
			
		}
		logger.info("ccc:{}, {}", rst.toString());
		return rstMap;
	}
	
	
	
//----------low level method --------------------------------------------------
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
//---------------------------------private metheo---------------------------------------

	private static String callSeveltService(String referer, String payload){
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
	
	private static Map<ElsHeader, String> getElsSingleDataMap(Document doc, String isin){
		Map<ElsHeader, String> rstMap = new HashMap<ElsHeader, String>();
		int cnt =0;
		ElsHeader tempHeader;
		
		for(Element e : doc.select("result").first().children()){
			cnt = cnt +1;
			tempHeader = new ElsHeader(isin, cnt, e.tagName());
//			tempHeader = new ElsHeader(isin, cnt, EKsdDataItem.valueOf(e.tagName()));
			rstMap.put(tempHeader, e.attr("value"));
		}
		
		return rstMap;
	}
	
	
	private static Map<ElsHeader, String> getElsListDataMap(Document doc, String isin){

		Map<ElsHeader, String> rstMap = new HashMap<ElsHeader, String>();
		ElsHeader tempHeader;
//		logger.info("xml : {}", doc.toString());
		int cnt =0;
		StringBuffer rst= new StringBuffer();
		for(Element e : doc.select("data>result")){
			cnt = cnt +1;
			for( Element bb : e.children()){
				rst.append(isin).append(";")
					.append(cnt).append(";")
					.append(bb.tagName()).append(";")
				   .append(bb.attr("value")).append("\n");
//				logger.info("bbb:{}, {}", bb.tagName(), bb.attr("value"));
				
//				tempHeader = new ElsHeader(isin, cnt, bb.tagName());
				tempHeader = new ElsHeader(isin, cnt, EKsdDataItem.valueOf(bb.tagName()));
				
				rstMap.put(tempHeader, bb.attr("value"));
				
			}
			
		}
//		logger.info("ccc:{}, {}", rst.toString());
		return rstMap;
	}
	
}
