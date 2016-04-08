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
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import fetchKrx.$6001KsdScrapUtilTest;

public class KsdMenu200T1 {
	private String payload;
	private String prePayload;
	private String referer;
	private static Properties properties = new Properties();

	public KsdMenu200T1() {

	}

	public Document getDocument(String isin) {
		Map<String, String> data = new HashMap<String, String>();
//		try {
//			properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
//			referer = properties.getProperty("[200_T1]referer");
//			payload = properties.getProperty("[200_T1]payload");

			referer ="http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200";
			payload ="<reqParam action=\"issuInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
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

	/*public String getJson(String isin) {
		List<Element> results = getDocument(isin).select("result");
		StringBuffer strBuffer = new StringBuffer();
//		strBuffer.append("[");
		
		for (Element aa : results) {
				strBuffer.append("{");
				strBuffer.append("\"isin\":\"").append(aa.select("isin").attr("value")).append("\"");
				strBuffer.append(",\"kor_secn_nm\":\"").append(aa.select("kor_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"rep_secn_nm\":\"").append(aa.select("rep_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"issu_dt\":\"").append(aa.select("issu_dt").attr("value")).append("\"");
				strBuffer.append(",\"xpir_dt\":\"").append(aa.select("xpir_dt").attr("value")).append("\"");
				strBuffer.append(",\"first_issu_qty\":").append(aa.select("first_issu_qty").attr("value"));
				strBuffer.append(",\"prcp_prsv_rate\":").append(aa.select("prcp_prsv_rate").attr("value"));
				strBuffer.append(",\"mid_cnt\":").append(aa.select("mid_cnt").attr("value"));
				strBuffer.append(",\"basset_cnt\":").append(aa.select("basset_cnt").attr("value"));
				strBuffer.append(",\"valat_price\":").append((aa.select("valat_price").attr("value").equals("")) ? "0" : aa.select("valat_price").attr("value"));
				strBuffer.append(",\"basset_last_valat_tpcd\":\"").append(aa.select("basset_last_valat_tpcd").attr("value")).append("\"");
				strBuffer.append(",\"scmnt_content\":\"").append(aa.select("scmnt_content").attr("value")).append("\"");
				strBuffer.append(",\"issu_cur_cd\":\"").append(aa.select("issu_cur_cd").attr("value")).append("\"");
				
				strBuffer.append("}");
		}
//		strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
//		strBuffer.append("]");
		return strBuffer.toString();
	}*/
	
	public String getEntityJson(String isin) {
		List<Element> results = getDocument(isin).select("result");
		StringBuffer strBuffer = new StringBuffer();
		
		for (Element aa : results) {
				strBuffer.append("{");
				strBuffer.append("\"isin\":\"").append(aa.select("isin").attr("value")).append("\"");
				strBuffer.append(",\"kor_secn_nm\":\"").append(aa.select("kor_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"rep_secn_nm\":\"").append(aa.select("rep_secn_nm").attr("value")).append("\"");
				strBuffer.append(",\"issu_dt\":\"").append(aa.select("issu_dt").attr("value")).append("\"");
				strBuffer.append(",\"xpir_dt\":\"").append(aa.select("xpir_dt").attr("value")).append("\"");
				strBuffer.append(",\"first_issu_qty\":").append(aa.select("first_issu_qty").attr("value"));
				strBuffer.append(",\"prcp_prsv_rate\":").append(aa.select("prcp_prsv_rate").attr("value"));
				strBuffer.append(",\"mid_cnt\":").append(aa.select("mid_cnt").attr("value"));
				strBuffer.append(",\"basset_cnt\":").append(aa.select("basset_cnt").attr("value"));
				strBuffer.append(",\"valat_price\":").append((aa.select("valat_price").attr("value").equals("")) ? "0" : aa.select("valat_price").attr("value"));
				strBuffer.append(",\"basset_last_valat_tpcd\":\"").append(aa.select("basset_last_valat_tpcd").attr("value")).append("\"");
				strBuffer.append(",\"scmnt_content\":\"").append(aa.select("scmnt_content").attr("value")).append("\"");
				strBuffer.append(",\"issu_cur_cd\":\"").append(aa.select("issu_cur_cd").attr("value")).append("\"");
				
				strBuffer.append("}");
		}
		return strBuffer.toString();
	}
	
	public String getListJson(String isin) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");
		strBuffer.append(getEntityJson(isin));
		strBuffer.append("]");

		return strBuffer.toString();
	}

	public Ksd200T1 getEntity(String isin) {

		Ksd200T1 rst = new Ksd200T1();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = mapper.readValue(getEntityJson(isin), Ksd200T1.class);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rst;
	}
	
	public List<Ksd200T1> getList(String isin) {
		List<Ksd200T1> rst = new ArrayList<Ksd200T1>();
		rst.add(getEntity(isin));
		return rst;
	}

}
