package com.eugenefe.scrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EDynKrxScrapper {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EDynKrxScrapper.class);
	private String name;
	private String url;
	private String optUrl;
	private String paramFile ;
	private Map<String, String> formData ; 
	private Map<String, String> parameterMeta = new HashMap<String, String>();
	
	private static List<EDynKrxScrapper> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EDynKrxScrapper.class);
	}
	public EDynKrxScrapper() {
	}
	
	public static List<EDynKrxScrapper> values(){
		return values;
	}
	
	public String getTargetClassName() {
		return "com.eugenefe.entity."+ getName();
	}
	
	
	@JsonIgnoreProperties
	public Map<String, String> getParameterMeta() {
		if(parameterMeta.size() > 0){
			return parameterMeta;
		}
		else{
			for(Map.Entry<String, String> entry : getFormData().entrySet()){
				String[] splitStr = entry.getValue().split("\\$\\{");
				if(splitStr.length==2){
					parameterMeta.put(splitStr[1].split("\\}")[0].toLowerCase(), entry.getKey());
				}
			}
			return parameterMeta;
		}
	}

	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public String getOptUrl() {
		return optUrl;
	}
	public Map<String, String> getFormData() {
		return formData;
	}
	
	public String getParamFile() {
		return paramFile;
	}

	public String getListJson() {
		int timeout = 1000000;
		StringBuffer rst = new StringBuffer();
		List<Map<String, String>> parameterList = getParameterData();
		
		for(Map<String, String> data : parameterList){
			StringBuffer strBuffer = new StringBuffer();
			filterParameters(data);
//			logger.info("formData : {}", getFormData());
			for (Map.Entry<String, String> entry : getParameterMeta().entrySet()) {
				strBuffer.append("\"para_").append(entry.getKey().toLowerCase()).append("\":\"").append(getFormData().get(entry.getValue())).append("\",");
			}
			rst.append(buildJson(strBuffer.toString(), scrap(getUrl(), getOptUrl(),  getFormData(), timeout)));
		}
		return rst.toString();
	}
	
	public List<Map<String, String>> getParameterData()  {
		try {
			String filePath =EFilePath.KRX_DATA.getFilePath() + getParamFile();
			String jsonString = FileUtil.readFile(filePath);
			return JsonUtil.convertToStringMap(jsonString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
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
			getFormData().put(entry.getValue(), rawData.get(entry.getKey()));
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
	
	public String scrap(String url, String optUrl, Map<String, String> formData, int timeout){
		String rst =null;
		try {
			Document optDoc = Jsoup.connect(optUrl).timeout(timeout).get();
			String optCode = optDoc.select("body").text();
			formData.put("code", optCode);

			Document rstDoc = Jsoup.connect(url)
					 			   .timeout(timeout)
					 			   .data(formData)
					 			   .post();
			
			rst = rstDoc.select("body").text();
//			logger.info("rst :{},{}", rst.split(element+ "\"\\s*:")[1]);
//			rst = rst.split(element+ "\"\\s*:")[1].split("\\]")[0];
//			return rst.split(element+ "\"\\s*:")[1];
			
			rst = rst.split("\\[")[1].split("\\]")[0];
			return "[" + rst + "]" ;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
