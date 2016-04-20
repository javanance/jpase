package com.eugenefe.scrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EDynKsdScrapper2 {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EDynKsdScrapper2.class);
	private String name;
	private String type;
	private String referer;
	private String action;
	private String task;
	private Map<String, String> tagData ; 
	private String headElement;
	private String attrName;
	private String paramFile;
//	private String payload;
	private Map<String, String> parameterMeta = new HashMap<String, String>();
	
	private static List<EDynKsdScrapper2> values ;
	
	
	static {
		values = JsonDynaEnum.convertTo(EDynKsdScrapper2.class);
	}
	
	public EDynKsdScrapper2() {
	}

	
	public static List<EDynKsdScrapper2> values(){
		return values;
	}
	
	public String getTargetClassName() {
		return "com.eugenefe.entity."+ this.toString();
	}

	public static String getUrl(){
		return  "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	}
	
	@JsonIgnoreProperties
	public Map<String, String> getParameterMeta() {
		if(parameterMeta.size() > 0){
			return parameterMeta;
		}
		else{
			for(Map.Entry<String, String> entry : getTagData().entrySet()){
				String[] splitStr = entry.getValue().split("\\$\\{");
				if(splitStr.length==2){
					parameterMeta.put(splitStr[1].split("\\}")[0].toLowerCase(), entry.getKey());
				}
			}
			return parameterMeta;
		}
	}
	
//	public List<Map<String, String>> getParameterData()  {
//		try {
//			String filePath =EFilePath.KSD_DATA.getFilePath() + getParamFile();
//			logger.info("json: {}", filePath);
//			String jsonString = FileUtil.readFile(filePath);
//			return JsonStringUtil.convertToMap(jsonString);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}
	
	public List<Map<String, String>> getParameterData()  {
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
	
	public String getListJson() {
		int timeout = 1000000;
		StringBuffer rst = new StringBuffer();
		List<Map<String, String>> parameterList = getParameterData();
		List<Element> results  = new ArrayList<Element>();
		for(Map<String, String> paramData : getParameterData()){
		
			StringBuffer strBuffer = new StringBuffer();
			filterParameters(paramData);
			
			logger.info("formData : {},{}", getTagData(),getPayload());
			for (Map.Entry<String, String> entry : getParameterMeta().entrySet()) {
				strBuffer.append("\"para_").append(entry.getKey().toLowerCase()).append("\":\"").append(getTagData().get(entry.getValue())).append("\",");
			}
//			TODO:
			results = getDocument(getReferer(), getPayload()).select(getHeadElement());
			rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
		}
		return "[" +rst.toString() + "]";
	}
	
//	public String getListJson() {
//		StringBuffer rst = new StringBuffer();
//		StringBuffer strBuffer = new StringBuffer();
//		List<Element> results  = new ArrayList<Element>();
//		
//		if(getParameterData().size()==0){
//			results = getDocument(getReferer(), getPayload()).select(getHeadElement());
//			rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
//		}
//		else {
//			List<Map<String, String>> paramDataList = new ArrayList<Map<String, String>>();
//			paramDataList = getParameterData();
//			for(Map<String, String> paramData : paramDataList){
//				strBuffer = new StringBuffer();
//				for (Map.Entry<String, String> entry : paramData.entrySet()) {
////					TODO  : para delete after json Mapping Test!!!
//					strBuffer.append("\"para_").append(entry.getKey().toLowerCase()).append("\":\"").append(entry.getValue()).append("\",");
//				}
//				
//				results = getDocument(getReferer(), StrSubstitutor.replace(getPayload(),paramData)).select(getHeadElement());
//				rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
//				
//			}
//			
//		}
//		return "[" +rst.toString() + "]";
//	}
			


	public String getListJson1() {
		StringBuffer rst = new StringBuffer();
		StringBuffer strBuffer = new StringBuffer();
		List<Element> results  = new ArrayList<Element>();
		
		if(getParamFile()== null || getParamFile()==""){
			results = getDocument(getReferer(), getPayload()).select(getHeadElement());
			logger.info("doc1 :{}", getPayload());
			rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
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
				logger.info("doc :{}", results);
				rst.append(buildJson(strBuffer.toString(), results, getAttrName(), getType()));
				
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

	public String getReferer() {
		return referer;
	}

	public String getAction() {
		return action;
	}

	public String getTask() {
		return task;
	}


	public String getPayload() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<reqParam action=").append(getAction());
		buffer.append(" task=").append(getTask()).append(">");
		
		for(Map.Entry<String, String> entry : getTagData().entrySet()){
			buffer.append("<").append(entry.getKey()).append(" value=").append(entry.getValue()).append("/>");
			
		}
		buffer.append("</reqParam>");
//		logger.info("payload: {}", buffer.toString());
		return buffer.toString();
	}
	
	public String getHeadElement() {
		return headElement;
	}
//	@JsonIgnoreProperties
	public Map<String, String> getTagData() {
		return tagData;
	}



	public String getAttrName() {
		return attrName;
	}

	public String getParamFile() {
		return paramFile;
	}
	
//********************** private method *******************
	
	private void filterParameters(Map<String, String> rawData){
		for(Map.Entry<String, String> entry : getParameterMeta().entrySet()){
//			logger.info("para: {},{}", entry.getKey(), entry.getValue());
			tagData.put(entry.getValue(), "\"" +rawData.get(entry.getKey()) + "\"");
		}
	}

	private String buildJson(String prefix, List<Element> elemenst, String attrName, String  type) {
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
	}

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
