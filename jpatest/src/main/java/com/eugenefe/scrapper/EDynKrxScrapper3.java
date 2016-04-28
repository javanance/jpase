package com.eugenefe.scrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class EDynKrxScrapper3 {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EDynKrxScrapper3.class);
	private String name;
	private String url;
	private String optUrl;
	private String paramFile ;
	private String paramElement ;
	private Map<String, String> formData ; 
	private Map<String, String> parameterMeta = new HashMap<String, String>();
	
	private static List<EDynKrxScrapper3> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EDynKrxScrapper3.class);
	}
	public EDynKrxScrapper3() {
	}
	
	public static List<EDynKrxScrapper3> values(){
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
//					parameterMeta.put(splitStr[1].split("\\}")[0].toLowerCase(), entry.getKey());
					parameterMeta.put(splitStr[1].split("\\}")[0], entry.getKey());
				}
			}
			return parameterMeta;
		}
	}


	public String getJson() {
		int timeout = 100000000;
		String  rst ="";
		String temp;
		int cnt =0;
		ArrayNode arrNode = JsonNodeFactory.instance.arrayNode();
		
		List<Map<String, String>> parameterList = getParameterData();
		if(parameterList.size()==0){
			temp = JsonUtil.attachElement(getJsonHeader(), null, "param");
			rst  = JsonUtil.attachElement(temp, scrap(getUrl(), getOptUrl(),  getFormData(), timeout));
		}
		else {
			for(Map<String, String> data : parameterList){
//				logger.info("parameterData :  {}, {}", data, getParameterMeta());
				cnt = cnt+1;
				filterParameters(data);
				if(formData.toString().contains("=null")){
					logger.warn("Parameter is not switched to data of line {} in  {} ",  getParamFile(), cnt );
				}
				else {
//   			        logger.info("formData : {}, {}",  parameterList.size(), scrap(getUrl(), getOptUrl(),  getFormData(), timeout));
					temp = JsonUtil.attachElement(getJsonHeader(), getJsonParam(data), "param");
//	    			rst = JsonUtil.addList(rst, JsonUtil.attachElement(temp, scrap(getUrl(), getOptUrl(),  getFormData(), timeout), "result"));
					rst = JsonUtil.addList(rst, JsonUtil.attachElement(temp, scrap(getUrl(), getOptUrl(),  getFormData(), timeout)));
				}
			}
			
		}
		return rst;
	}
	
	public List<Map<String, String>> getParameterData()  {
		String elementJson;
		try {
			String defJsonString =FileUtil.readFile(EFilePath.KRX_DATA.getBaseParamPath());
			if(getParamFile()== null || getParamFile()==""){
				return JsonUtil.convertToStringMap(defJsonString);
			}
			else{
				String fileJsonString = FileUtil.readFile(EFilePath.KRX_DATA.getFilePath() + getParamFile());
				elementJson = JsonUtil.getElements(fileJsonString, getParamElement()).toString();
//				logger.info("json param : {},{}", elementJson, fileJsonString);
//				logger.info("json param : {},{}",  JsonUtil.getElements(fileJsonString, getParamElement()));
				if(elementJson.equals("[]") || elementJson.equals("{}")){
					return JsonUtil.convertToStringMap(defJsonString);
				}
				else{
//					logger.info("json param : {},{}",  defJsonString, elementJson);
					String jsonString = JsonUtil.attachElement(defJsonString, elementJson);
//				    logger.info("json param : {},{}",  defJsonString, elementJson);
					
					return JsonUtil.convertToStringMap(jsonString);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
//	***************************getter setter **************************************
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
	public String getParamElement() {
		return paramElement;
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
	
	private String getJsonHeader(){
		Map<String, String> rst = new HashMap<String, String>();
		rst.put("name", getName());
		return JsonUtil.convertToJson(rst);
	}
	
	private String getJsonParam (Map<String, String> rawData){
		Map<String, String> rst = new HashMap<String, String>();
		
		for(Map.Entry<String, String> entry : getParameterMeta().entrySet()){
			rst.put(entry.getValue(), rawData.get(entry.getKey()));
		}
		return JsonUtil.convertToJson(rst);
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
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
