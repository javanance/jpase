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
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdScrapUtilTest;

public class KsdMenu200P {
	private String payload;
	private String prePayload;
	private String referer;
	private static Properties properties = new Properties();

	public KsdMenu200P() {

	}

	public Document getDocument(String comCode) {
//		Map<String, String> preData = new HashMap<String, String>();
		Map<String, String> data = new HashMap<String, String>();
		try {
//			properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
//			referer = properties.getProperty("[200_P]referer");
//			payload = properties.getProperty("[200_P]payload");

			referer ="http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/etc/BIP_CMUC01054P.xml&secn_tpcd=41";
			payload ="<reqParam action=\"searchIsscoBySecnList\" task=\"ksd.safe.bip.cmuc.User.process.SearchPTask\">"
					+ "<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/>"
//					+ "<KOR_SECN_NM value=\"\"/>"
					+ "<SECN_TPCD value=\"41\"/>"
					+ "</reqParam>";
			
			data.put("ISSUCO_CUSTNO", comCode );

			return Jsoup.parse(KsdUtil.callSeveltService(referer, StrSubstitutor.replace(payload, data)));

		} catch (Exception ex) {
			return null;
		}
	}

	public String getListJson(String comCode) {
		String rst = getDocument(comCode).select("line").attr("value");
		String[] rst1 = rst.split("\\^\\!\\^\\!");
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

		for (String aa : rst1) {
				String[] rst2 = aa.split("\\^\\|");
				strBuffer.append("{");
				strBuffer.append("\"isin\":\"").append(rst2[0]).append("\"");
				strBuffer.append(",\"issuco_custno\":\"").append(rst2[1]).append("\"");
				strBuffer.append(",\"kor_secn_nm\":\"").append(rst2[2].split("\\^\\!")[0]).append("\"");
				strBuffer.append("},");
		}
		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		strBuffer.append("]");
		return strBuffer.toString();
	}

	public List<Ksd200P> getList(String comCode) {

		List<Ksd200P> rst = new ArrayList<Ksd200P>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = Arrays.asList(mapper.readValue(getListJson(comCode), Ksd200P[].class));

			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rst;
	}

}
