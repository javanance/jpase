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
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdDataTest;

public class KsdMenu200T2 {
	private String payload;
	private String prePayload;
	private String referer;
	private static Properties properties = new Properties();

	public KsdMenu200T2() {

	}

	public Document getDocument(String isin) {
		Map<String, String> data = new HashMap<String, String>();
//		try {
//			properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
//			referer = properties.getProperty("[200_T2]referer");
//			payload = properties.getProperty("[200_T2]payload");

			referer ="http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200";
			payload ="<reqParam action=\"bassetXrcList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
					+ "<MENU_NO value=\"200\"/>"
					+ "<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
					+ "<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>"
					+ "<ISIN value=\"${ISIN}\"/>"
					+ "</reqParam>";
		
			data.put("ISIN", isin );

			return Jsoup.parse(KsdUtil.callSeveltService(referer, StrSubstitutor.replace(payload, data)));

//		} catch (Exception ex) {
//			return null;
//		}
	}

	
	public String getListJson(String isin) {
		List<Element> results = getDocument(isin).select("result");

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

		for (Element aa : results) {
				strBuffer.append("{");
				strBuffer.append("\"isin\":\"").append(isin).append("\"");
				strBuffer.append(",\"kor_secn_nm\":\"").append(aa.select("kor_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"xrc_std_ratio\":").append(aa.select("xrc_std_ratio").attr("value"));
				strBuffer.append(",\"xrc_stdprc\":").append(aa.select("xrc_stdprc").attr("value"));
				strBuffer.append(",\"scmnt_content\":\"").append(aa.select("scmnt_content").attr("value")).append("\"");
				strBuffer.append("},");
		}
		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		strBuffer.append("]");
		String rst = strBuffer.toString();
		return rst;
	}

	
	
	public List<Ksd200T2> getList(String isin) {
		List<Ksd200T2> rst = new ArrayList<Ksd200T2>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = Arrays.asList(mapper.readValue(getListJson(isin), Ksd200T2[].class));
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
