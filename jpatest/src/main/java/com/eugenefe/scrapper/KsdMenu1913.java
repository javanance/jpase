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
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdDataTest;

public class KsdMenu1913 {
	private String payload;
	private String prePayload;
	private String referer;
	private static Properties properties = new Properties();

	public KsdMenu1913() {

	}

	public Document getDocument(String baseDate) {
//		Map<String, String> preData = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		try {
//			properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
//			referer = properties.getProperty("[191_T3]referer");
//			prePayload = properties.getProperty("[191_T3]prePayload");
//			payload = properties.getProperty("[191_T3]payload");

			referer ="http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml&menuNo=191";
			prePayload ="<reqParam action=\"newnIssuSecnListCnt\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriCommPTask\">"
					     + "<SECN_TPNM value=\"ELS\"/>"
					     + "<STD_DT value=\"${STD_DT}\"/>"
//					     + "<MENU_NO value=\"191\"/>"
//					     + "<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,comparison,favorites,more2,more2,link,link,wide,wide,top,\"/>"
//					     + "<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml\"/>"
//					     + "<START_PAGE value=\"1\"/>"
//					     + "<END_PAGE value=\"10\"/>"
					     + "</reqParam>";
			
			payload ="<reqParam action=\"newnIssuSecnPList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriCommPTask\">"
					+ "<STD_DT value=\"${STD_DT}\"/>"
					+ "<START_PAGE value=\"1\"/>"
					+ "<END_PAGE value=\"${END_PAGE}\"/>"
//					+ "<SECN_TPNM value=\"ELS\"/>"
//					+ "<MENU_NO value=\"191\"/>"
//					+ "<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,comparison,favorites,more2,more2,link,link,wide,wide,top,\"/>"
//					+ "<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml\"/>"
					+ "</reqParam>";
			
			data.put("STD_DT", baseDate);
			Document doc = Jsoup.parse(KsdUtil.callSeveltService(referer,  StrSubstitutor.replace(prePayload, data)));
			data.put("END_PAGE", doc.select("list_cnt").attr("value"));

			return Jsoup.parse(KsdUtil.callSeveltService(referer, StrSubstitutor.replace(payload, data)));

		} catch (Exception ex) {
			return null;
		}
	}

	public String getListJson(String baseDate) {
		List<Element> results = getDocument(baseDate).select("result");

		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

		for (Element aa : results) {
			if (aa.childNodeSize() == 9) {
				strBuffer.append("{");
				strBuffer.append("\"rep_secn_nm\":\"").append(aa.select("rep_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"isin\":\"").append(aa.select("isin").attr("value")).append("\"");
				strBuffer.append(",\"kor_secn_nm\":\"").append(aa.select("kor_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"issu_dt\":\"").append(aa.select("issu_dt").attr("value")).append("\"");
				strBuffer.append(",\"xpir_dt\":\"").append(aa.select("xpir_dt").attr("value")).append("\"");
				strBuffer.append(",\"first_issu_qty\":").append(aa.select("first_issu_qty").attr("value"));
				strBuffer.append(",\"minhi_guar_rate\":").append(aa.select("minhi_guar_rate").attr("value"));
				strBuffer.append(",\"secn_tpnm\":\"").append(aa.select("secn_tpnm").attr("value")).append("\"");
//				strBuffer.append(",\"secn_tpnm\":").append(aa.select("secn_tpnm").attr("value"));
				strBuffer.append("},");
			}
		}
		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		strBuffer.append("]");
		String rst = strBuffer.toString();
		return rst;
	}

	public List<Ksd191T3> getList(String baseDString) {

		List<Ksd191T3> rst = new ArrayList<Ksd191T3>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = Arrays.asList(mapper.readValue(getListJson(baseDString), Ksd191T3[].class));

			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rst;
	}

}
