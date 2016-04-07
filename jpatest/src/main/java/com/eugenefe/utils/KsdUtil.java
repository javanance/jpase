package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.enums.EKsdDataItem;
import com.eugenefe.enums.EKsdElsDataGroup;
import com.eugenefe.model.ElsHeader;


public class KsdUtil {
	private final static Logger logger = LoggerFactory.getLogger(KsdUtil.class);	
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
	
	
	public static Document getAAA(String referer, String payload){
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, payload), "",Parser.xmlParser());
		return doc;
	}
	
	public static Document getElsDataDoc(EKsdElsDataGroup dataType, String isin){
		
//		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		String payload =getPayLoad(dataType, isin);
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, payload), "",Parser.xmlParser());
		
//		logger.info("DOC: {}", doc);
		return doc;
	}
	
	
	public static Map<ElsHeader, String> getElsDataMap(EKsdElsDataGroup dataType, String isin){
		
		
		
//		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		String payload = getPayLoad(dataType, isin);
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, payload), "",Parser.xmlParser());
		
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
		
		
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, issueInfoPayload), "",Parser.xmlParser());
		
		return doc;
	}
	
	public static Document getElsStrikePrice(String isin){
		String exerciseAction = "bassetXrcList";
		String exerPayload =String.format(_elsPayLoad, exerciseAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);

		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, exerPayload),"", Parser.xmlParser());
		return doc;
	}
	
	public static Document getElsSchedule(String isin){
		String scheduleAction = "midValatSkedulRedCondiList";
		String schedulePayload =String.format(_elsPayLoad, scheduleAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, schedulePayload),"", Parser.xmlParser());
//		logger.info("Doc : {}", doc);
		return doc;
	}
	
	public static Document getElsBaseInfo(String isin){
		String baseInfoAction = "bassetInfoList";
		String baseInfoPayload =String.format(_elsPayLoad, baseInfoAction, elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		Document doc = Jsoup.parse(KsdUtil.callSeveltService(elsReferer, baseInfoPayload),"", Parser.xmlParser());
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

	public static String callSeveltService(String referer, String payload){
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
//	
//	<reqParam action=\"%s\" task=\"%s\"><MENU_NO value=\"%s\"/><CMM_BTN_ABBR_NM value=\"%s\"/><W2XPATH value=\"%s\"/><ISIN value=\"%s\"/></reqParam>";
//	
//	dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin
//	
	private static String getPayLoad(EKsdElsDataGroup dataType,  String isin){
		Map<String, String> data = new HashMap<String, String>();
		
		String template ="<reqParam action=\"${action}\" task=\"${task}\">"
				+ "<SHOTN_ISIN value=\"${SHOTN_ISIN}\"/>"
				+ "<ISIN value=\"${ISIN}\"/> "
				+ "<MENU_NO value=\"${MENU_NO}\"/> "
				+ "<CMM_BTN_ABBR_NM value=\"${CMM_BTN_ABBR_NM}\"/>"
				+ "<W2XPATH value=\"${W2XPATH}\"/> "
				+ "<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/> "
				+ "<STD_DT value=\"${STD_DT}\"/> "
				+ "<ic_end value=\"${ic_end}\"/> "
				+ "<ic_start value=\"${ic_start}\"/> "
				+ "<ROWNUM value=\"${ROWNUM}\"/> "
				+ "<START_PAGE value=\"${START_PAGE}\"/> "
				+ "<END_PAGE value=\"${END_PAGE}\"/>"
				+ "<FNA value=\"${FNA}\"/>"
				+ "<FNA_TYPE value=\"${FNA_TYPE}\"/> "
				+ "<SETACC_YYMM1 value=\"${SETACC_YYMM1}\"/> "
				+ "<SETACC_YYMM2 value=\"${SETACC_YYMM2}\"/> "
				+ "<SETACC_YYMM3 value=\"${SETACC_YYMM3}\"/> "
				+ "</reqParam>";
		
		data.put("action", dataType.getSebCode()) ;
		data.put("task", "ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask");
		data.put("SHOTN_ISIN", "009150");
		data.put("ISIN", isin);
		data.put("MENU_NO", "200");
		data.put("CMM_BTN_ABBR_NM", "allview,allview,print,hwp,word,pdf,searchIcon,seach,favorites float_left,item float_left,more2,more2,more2,more2,more2 m_left5,more2,more2,more2,more2,link,link,wide,wide,top,");
		data.put("W2XPATH", "/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml");
		data.put("ISSUCO_CUSTNO", "915");
		data.put("STD_DT", "20160331");
		data.put("ic_end", "20160331");
		data.put("ic_start", "20150331");
		data.put("ROWNUM", "5");
		data.put("START_PAGE", "1");
		data.put("END_PAGE", "10");
		data.put("FNA", "60");
		data.put("FNA_TYPE", "별도");
		data.put("SETACC_YYMM1", "2013");
		data.put("SETACC_YYMM2", "2014");
		data.put("SETACC_YYMM3", "2015");
		data.put("zzz", "2015");
		
//		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		return StrSubstitutor.replace(template, data);
	}
	
	private static String getPayLoad1(){
		Map<String, String> data = new HashMap<String, String>();
		
		String template ="<reqParam action=\"${action}\" task=\"${task}\">"
				+ "<SHOTN_ISIN value=\"${SHOTN_ISIN}\"/>"
				+ "<ISIN value=\"${ISIN}\"/> "
				+ "<MENU_NO value=\"${MENU_NO}\"/> "
				+ "<CMM_BTN_ABBR_NM value=\"${CMM_BTN_ABBR_NM}\"/>"
				+ "<W2XPATH value=\"${W2XPATH}\"/> "
				+ "<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/> "
				+ "<STD_DT value=\"${STD_DT}\"/> "
				+ "<ic_end value=\"${ic_end}\"/> "
				+ "<ic_start value=\"${ic_start}\"/> "
				+ "<ROWNUM value=\"${ROWNUM}\"/> "
				+ "<START_PAGE value=\"${START_PAGE}\"/> "
				+ "<END_PAGE value=\"${END_PAGE}\"/>"
				+ "<FNA value=\"${FNA}\"/>"
				+ "<FNA_TYPE value=\"${FNA_TYPE}\"/> "
				+ "<SETACC_YYMM1 value=\"${SETACC_YYMM1}\"/> "
				+ "<SETACC_YYMM2 value=\"${SETACC_YYMM2}\"/> "
				+ "<SETACC_YYMM3 value=\"${SETACC_YYMM3}\"/> "
				+ "</reqParam>";
		
		data.put("action", "stkIsueList") ;
		data.put("task", "ksd.safe.bip.cnts.Stock.process.SecnInfoPTask");
		data.put("SHOTN_ISIN", "009150");
		data.put("ISIN", "KR7009150004");
		data.put("MENU_NO", "44");
		data.put("CMM_BTN_ABBR_NM", "allview,allview,print,hwp,word,pdf,searchIcon,seach,favorites float_left,item float_left,more2,more2,more2,more2,more2 m_left5,more2,more2,more2,more2,link,link,wide,wide,top,");
		data.put("W2XPATH", "/IPORTAL/user/stock/BIP_CNTS02006V.xml");
		data.put("ISSUCO_CUSTNO", "915");
		data.put("STD_DT", "20160331");
		data.put("ic_end", "20160331");
		data.put("ic_start", "20150331");
		data.put("ROWNUM", "5");
		data.put("START_PAGE", "1");
		data.put("END_PAGE", "10");
		data.put("FNA", "60");
		data.put("FNA_TYPE", "별도");
		data.put("SETACC_YYMM1", "2013");
		data.put("SETACC_YYMM2", "2014");
		data.put("SETACC_YYMM3", "2015");
		data.put("zzz", "2015");
		
//		String payload =String.format(_elsPayLoad, dataType.getSebCode(), elsTask, elsMenuNo, elsCmmBtn, elsXpath, isin);
		
		return StrSubstitutor.replace(template, data);
	}
}
