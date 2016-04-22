package com.eugenefe.scrapper;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EDynKrxApi {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EDynKrxApi.class);
	private String name;
	private String resultElement;
	private String url;
	private String paramFile ;
	private Map<String, String> formData = new HashMap<String, String>();
	private Map<String, String> parameterMeta = new HashMap<String, String>();
	
	private static List<EDynKrxApi> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EDynKrxApi.class);
	}
	public EDynKrxApi() {
	}
	
	public static List<EDynKrxApi> values(){
		return values;
	}
	
	public String getTargetClassName() {
		return "com.eugenefe.entity."+ getName();
	}
	
	@JsonIgnoreProperties
	public Map<String, String> getFormData(){
		if(formData.size() > 0){
			return formData;
		}
		else{ 
			formData = getParameterMeta();
			return formData;
		}
		
	}
	@JsonIgnoreProperties
	public Map<String, String> getParameterMeta() {
		String[] firtSplitStr ;
		String[] secondSplitStr;
		String[] thirdSplitStr;
		if(parameterMeta.size() > 0){
			return parameterMeta;
		}
		else{
			firtSplitStr = getUrl().split("\\?");
			if(firtSplitStr.length==2){
				secondSplitStr = firtSplitStr[1].split("\\&");
				for( String bb : secondSplitStr){
					thirdSplitStr = bb.split("=");
					if(thirdSplitStr.length==2 && thirdSplitStr[1].trim().startsWith("$")){
						parameterMeta.put(thirdSplitStr[0], thirdSplitStr[1].trim().replaceAll("(\\$\\{|\\})", ""));
					}
				}
			}
			return parameterMeta;
		}
	}

	public String getName() {
		return name.trim();
	}
	public String getUrl() {
		return url.trim();
	}
	
	public String getResultElement() {
		return resultElement.trim();
	}

	
	public String getParamFile() {
		return paramFile;
	}

	public List<Map<String, String>> getParameterData()  {
		List<Map<String, String>> temp = new ArrayList<Map<String,String>>();
		Map<String, String>  tempMap =  new HashMap<String, String>();
		Map<String, String>  tempMap1 =  new HashMap<String, String>();
		tempMap.put("isuSrtCd", "000250");
		tempMap1.put("isuSrtCd", "000440");
		temp.add(tempMap1);
		temp.add(tempMap);
		return temp;
//		try {
//			String filePath =EFilePath.KRX_DATA.getFilePath() + getParamFile();
//			String jsonString = FileUtil.readFile(filePath);
//			return JsonUtil.convertToMap(jsonString);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
	}
	
	public String getListJson() {
		int timeout = 1000000;
		StringBuffer rst = new StringBuffer();
		List<Map<String, String>> parameterList = getParameterData();
		if(parameterList==null || parameterList.size()==0){
			rst.append("{");
			rst.append("\"name\":\"").append(getName()).append("\",");
			rst.append("\"param\":{");
			logger.info("aaa : {}",  parameterList);
        	logger.info("aaa : {}",  rst.toString());
        	rst.append("},");
        	rst.append("\"results\":").append(scrap(getUrl(), timeout));
			rst.append("}");
        }
        else{
        	for(Map<String, String> data : parameterList){
        		filterParameters(data);
        		
    			rst.append("{");
        		rst.append("\"name\":\"").append(getName()).append("\",");
        		rst.append("\"param\":{");
        

				for(Map.Entry<String, String> entry : getFormData().entrySet()){
					rst.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
				}
				if(rst.lastIndexOf(",") > 0){
					rst.deleteCharAt(rst.lastIndexOf(","));
				}
				rst.append("},");
				rst.append("\"result\":").append(scrap(StrSubstitutor.replace(getUrl(),data), timeout));
				rst.append("},");
//				rst.append("\"results\":[").append(scrap(StrSubstitutor.replace(getUrl(),data), timeout));
//				rst.append("]},");
        	}
		}
		if(rst.lastIndexOf(",") > 0){
			rst.deleteCharAt(rst.lastIndexOf(","));
		}
		return "[" + rst.toString() +"]";
	}
	
	
	

	// *****************************private method********************************************************************
	
	private <E> List<E> convertTo(String jsonString) {
		try {
//			logger.info("klass: {}", getTargetClassName());
			Class klass = Class.forName(getTargetClassName());
			return JsonUtil.convertTo(klass, jsonString);
		} catch (ClassNotFoundException ex) {
			logger.error("ClasssNotFoundError for {}", getName());
		}
		return null;
	}
	
	private void filterParameters(Map<String, String> rawData){
		for(Map.Entry<String, String> entry : getParameterMeta().entrySet()){
//			logger.info("para: {},{}", entry.getKey(), entry.getValue());
			formData.put(entry.getValue(), rawData.get(entry.getKey()));
		}
	}
	
	private String buildJson(String prefix,  String jsonString) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

//		logger.info("split : {}", jsonString.split("\\{")[1]);
		
		for (String aa : jsonString.split("\\{")) {
			if(!aa.equals("[")){
				strBuffer.append("{");
				if (prefix != null) {
					strBuffer.append(prefix);
				}
				strBuffer.append(aa).append("\n");
			}
		}
		String rst = strBuffer.toString();
		return rst;
	}
	
	public String scrap(String url, int timeout){
		String rst =null;
		try {
//			Document rstDoc = Jsoup.connect(url).ignoreContentType(true).timeout(timeout).get();
			Document rstDoc = Jsoup.connect(url).ignoreContentType(true).get();
			rst = rstDoc.select("body").text();
			return rst.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
