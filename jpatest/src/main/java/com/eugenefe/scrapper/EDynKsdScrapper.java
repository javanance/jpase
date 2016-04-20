package com.eugenefe.scrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;

public class EDynKsdScrapper {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EDynKsdScrapper.class);
	private String name;
	private String type;
	private String referer;
	private String payload;
	private String headElement;
	private String attrName;
	private String paramFile;
	
	private static List<EDynKsdScrapper> values ;
	
	
	static {
		values = JsonDynaEnum.convertTo(EDynKsdScrapper.class);
	}
	
	public EDynKsdScrapper() {
	}

	public EDynKsdScrapper(String referer, String payload) {
		this.referer = referer;
		this.payload = payload;
	}
	
	public static List<EDynKsdScrapper> values(){
		return values;
	}
	
	public String getTargetClassName() {
		return "com.eugenefe.entity."+ this.toString();
	}

	public static String getUrl(){
		return  "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	}
	
	public List<String> getParameters()  {
		List<String> rst = new ArrayList<String>();
		String[] splitStr = getPayload().split("\\$\\{");
		for (int i = 1; i < splitStr.length; i++) {
			rst.add(splitStr[i].split("\\}")[0].toLowerCase());
		}
		return rst;
	}
	
	public String getListJson() {
		StringBuffer rst = new StringBuffer();
		StringBuffer strBuffer = new StringBuffer();
		List<Element> results  = new ArrayList<Element>();
		List<String> paraMeta  = getParameters();
		String rstJsonString ="";
		
		if(getParameterData().size()==0){
			results = getDocument(getReferer(), getPayload()).select(getHeadElement());
			return convertToListJson(results, getAttrName());
		}
		else {
			for(Map<String, String> paramData : getParameterData()){
				strBuffer = new StringBuffer();
				strBuffer.append("{");
				for (Map.Entry<String, String> entry : paramData.entrySet()) {
					if(paraMeta.contains(entry.getKey())){
						strBuffer.append("\"").append(entry.getKey().toLowerCase()).append("\":\"").append(entry.getValue()).append("\",");
					}
				}
				strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
				strBuffer.append("}");
				
//				logger.info("filterParamJson: {},{}",  strBuffer.toString(), StrSubstitutor.replace(getPayload(),paramData));
//				logger.info("doc :{},{}",  getAttrName() ,results);
//				logger.info("convertToListjson :{}", convertToListJson(results, getAttrName()));
//				logger.info("merge :{}", JsonStringUtil.merge(strBuffer.toString(), convertToListJson(results, getAttrName())));
//				logger.info("rstJsonString :{}", rstJsonString);
				results = getDocument(getReferer(), StrSubstitutor.replace(getPayload(),paramData)).select(getHeadElement());
				rstJsonString = JsonUtil.addList(rstJsonString, JsonUtil.merge(strBuffer.toString(), convertToListJson(results, getAttrName())));
			}
		}
		return rstJsonString;
	}
			
	public String getListJson1() {
		StringBuffer rst = new StringBuffer();
		StringBuffer strBuffer = new StringBuffer();
		List<Element> results  = new ArrayList<Element>();
		
		if(getParamFile()== null || getParamFile()==""){
			results = getDocument(getReferer(), getPayload()).select(getHeadElement());
			logger.info("doc1 :{}", getPayload());
//			rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
			return convertToListJson(results, getAttrName());
		}
		else {
			List<Map<String, String>> paramDataList = new ArrayList<Map<String, String>>();
			paramDataList = getParameterData();
			for(Map<String, String> paramData : paramDataList){
				strBuffer = new StringBuffer();
				for (Map.Entry<String, String> entry : paramData.entrySet()) {
					strBuffer.append("\"para_").append(entry.getKey().toLowerCase()).append("\":\"").append(entry.getValue()).append("\",");
				}
				
				results = getDocument(getReferer(), StrSubstitutor.replace(getPayload(),paramData)).select(getHeadElement());
				logger.info("doc :{}", convertToListJson(results, getAttrName()));
//				rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
				
			}
			
		}
		return "[" +rst.toString() + "]";
	}
	
	public Document getDocument(String referer, String payload) {
		return Jsoup.parse(callSeveltService(referer, payload));
	}
	
//	**************** gettter method********************
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferer() {
		return referer;
	}

	public String getPayload() {
		return payload;
	}
	
	public String getHeadElement() {
		return headElement;
	}

	public void setHeadElement(String headElement) {
		this.headElement = headElement;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getParamFile() {
		return paramFile;
	}
	
//********************** private method *******************
	
	public List<Map<String, String>> getParameterData()  {
//		List<Map<String, String>> rst = new ArrayList<Map<String, String>>();
		try {
			String defJsonString =FileUtil.readFile(EFilePath.KSD_DATA.getBaseParamPath());
			
			if(getParamFile()== null || getParamFile()==""){
				return JsonUtil.convertToMap(defJsonString);
			}
			else{
				String fileJsonString = FileUtil.readFile(EFilePath.KSD_DATA.getFilePath() + getParamFile());

				String jsonString = JsonUtil.merge(JsonUtil.extractEntityJson(defJsonString), fileJsonString);
//				logger.info("json : {},{}",  jsonString, fileJsonString);
				return JsonUtil.convertToMap(jsonString);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private String convertToListJson(List<Element> elemenst, String attrName){
		StringBuffer strBuffer = new StringBuffer();
		String tempStr;
		strBuffer.append("[");
		
		if(elemenst.size()==1 && elemenst.get(0).children().size() == 0){
			return "";
		}
		
		for (Element aa : elemenst) {
			strBuffer.append("{");
			for (Element bb : aa.children()) {
//				logger.info("converting :{}", strBuffer.toString());
				strBuffer.append("\"").append(bb.tagName()).append("\":\"").append(bb.attr(attrName)).append("\"");
				strBuffer.append(",");
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("},");
		}
		if (strBuffer.lastIndexOf(",") > 0) {
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		}
		strBuffer.append("]");
		String rst = strBuffer.toString();
		return rst;
	}

/*	private String buildJson(String prefix, List<Element> elemenst, String attrName, String  type) {
		StringBuffer strBuffer = new StringBuffer();
		String tempStr;
		for (Element aa : elemenst) {
			strBuffer.append("{");
			if (prefix != null) {
				strBuffer.append(prefix);
			}
			for (Element bb : aa.children()) {
				strBuffer.append("\"").append(bb.tagName()).append("\":");
				tempStr = bb.attr(attrName);
				if (type.equals("parameter")){
					strBuffer.append("\"").append(tempStr).append("\"");
				}
				else if(bb.tagName().contains("_dt") && tempStr.length()==8){
					strBuffer.append("\"").append(tempStr).append("\"");
				}
				else{
					try {
						strBuffer.append(Integer.parseInt(tempStr));
					} catch (NumberFormatException nfe) {
						try {
							strBuffer.append(Double.parseDouble(tempStr));
						} catch (NumberFormatException nfee) {
							if (tempStr.equals("")) {
								tempStr = "null";
								strBuffer.append(tempStr);
							} else {
								strBuffer.append("\"").append(tempStr).append("\"");
							}
							
						}
					}
				}

				strBuffer.append(",");
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("},");
		}

		if (strBuffer.lastIndexOf(",") > 0) {
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
		}
		String rst = strBuffer.toString();
		return rst;
	}*/

	public String callSeveltService(String referer, String payload) {
		Document doc;

		try {
//			URL obj = new URL(url);
			URL obj = new URL(getUrl());
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

			// conn.addRequestProperty("User-Agent", "Mozilla");
			// conn.addRequestProperty("Accept-Language",
			// "ko,en-US;q=0.8,en;q=0.6" );
			// conn.addRequestProperty("Content-Type", "application/xml");
			conn.addRequestProperty("Referer", referer);
			// conn.addRequestProperty("Referer", "" );
			// logger.info("referer: {}", referer);

			conn.setDoOutput(true);
			conn.setDoInput(true);

			OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			w.write(payload);
			w.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer html = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine).append("\n");
			}

			in.close();
			conn.disconnect();
			return html.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
}
