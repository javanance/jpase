package com.eugenefe.scrapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.eugenefe.entity.Ksd191T3;
import com.eugenefe.entity.Ksd200P;
import com.eugenefe.entity.Ksd200T1;
import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.Ksd200T3;
import com.eugenefe.entity.Ksd200T5;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdScrapUtilTest;

public class KsdMenu200T5 {
	private String payload;
	private String prePayload;
	private String referer;
	
	private static Properties properties = new Properties();

	public KsdMenu200T5() {

	}

	public Document getDocument(String isin) {
		Map<String, String> data = new HashMap<String, String>();
		// try {
		// properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
		// referer = properties.getProperty("[200_T3]referer");
		// payload = properties.getProperty("[200_T3]payload");

		referer = "http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200";
		payload = "<reqParam action=\"bassetInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ "<MENU_NO value=\"200\"/>"
				+ "<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ "<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>" + "<ISIN value=\"${ISIN}\"/>"
				+ "</reqParam>";

		data.put("ISIN", isin);

		return Jsoup.parse(KsdUtil.callSeveltService(referer, StrSubstitutor.replace(payload, data)));

		// } catch (Exception ex) {
		// return null;
		// }
	}
	
	public Document getDocument(String referer, String payload, Map<String, String> data) {
		return Jsoup.parse(KsdUtil.callSeveltService(referer, StrSubstitutor.replace(payload, data)));
	}
	

	public String getListJson(String isin) {
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
			
				try {
					strBuffer1.append(Integer.parseInt(tempStr));
				} catch (NumberFormatException nfe) {
					try{
						strBuffer1.append(Double.parseDouble(tempStr));
					}
					catch(NumberFormatException nfee){
						strBuffer1.append("\"").append(tempStr).append("\"");
						
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
	}

}
