package com.eugenefe.enums;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.utils.FilePathUtil;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;
import com.eugenefe.utils.KrxScrapUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class EKrxMenuDyn {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(KrxScrapUtil.class);
	private static Properties prop = new Properties();
	
	private String name;
	private String url;
	private String optUrl;
	private String element;
	private Map<String, String> formData ; 
	private List<String> parameters = new ArrayList<String>();
	private String paramFile ;
	
	private static List<EKrxMenuDyn> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EKrxMenuDyn.class);
	}
	
	
	public EKrxMenuDyn() {
	}

	
	public static List<EKrxMenuDyn> values(){
		return values;
	}
	
	public String getTargetClassName() {
		return "com.eugenefe.entity."+ getName();
	}
	
	@JsonIgnoreProperties
	public List<String> getParameters(){
		if(parameters.size() > 0){
//			System.out.println("call "+ parameters.size());
			return parameters;
		}
		else{
			for(Map.Entry<String, String> entry : getFormData().entrySet()){
				String[] splitStr = entry.getValue().split("\\$\\{");
				if(splitStr.length==2){
					parameters.add(splitStr[1].split("\\}")[0]);
				}
			}
//			System.out.println("call2 "+ parameters.size());
			return parameters;
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
	public String getElement() {
		return element;
	}
	public Map<String, String> getFormData() {
		return formData;
	}
	
	public String getParamFile() {
		return paramFile;
	}

	public String getListJsonAll() throws IOException{
		int timeout = 1000000;
		StringBuffer rst = new StringBuffer();
		
		
		List<Map<String, String>> list = getParaList();
		logger.info("list : {}", list);
		
		for(Map<String, String> kk : list){
			filterParameters(kk);
			StringBuffer strBuffer = new StringBuffer();
			for (String para : getParameters()) {
				strBuffer.append("\"para_").append(para.toLowerCase()).append("\":\"").append(getFormData().get(para)).append("\",");
			}
			rst.append(buildJson(strBuffer.toString(), scrap(getUrl(), getOptUrl(),  getFormData(), timeout)));
		}
		return rst.toString();
//		logger.info("strBuffer: {},{}", strBuffer.toString());
//		return buildJson(strBuffer.toString(), scrap(getUrl(), getOptUrl(), getElement(), getFormData(), timeout));
	}
	
	public String getListJson(Map<String, String> rawData) {
		int timeout = 1000000;
		StringBuffer strBuffer = new StringBuffer();
		
		for (String para : getParameters()) {
			strBuffer.append("\"").append(para.toLowerCase()).append("\":\"").append(getFormData().get(para)).append("\",");
		}
		filterParameters(rawData);
//		logger.info("strBuffer: {},{}", strBuffer.toString());
		return buildJson(strBuffer.toString(), scrap(getUrl(), getOptUrl(),  getFormData(), timeout));
	}
	
	public <E> List<E> convertTo(Map<String, String> rawData) {
		return convertTo(getListJson(rawData));
	}
	
	public List<Map<String, String>> getParaList() throws IOException {
//		try {
			String filePath = FilePathUtil.getFilePath("krxDataPath")+ getParamFile();
			logger.info("filepath : {},{}", FilePathUtil.getFilePath("krxDataPath")+ getParamFile());
			logger.info("filepath2 : {},{}", FileUtil.readFile(filePath));
			String jsonString = FileUtil.readFile(filePath);
			
			
			return JsonUtil.convertToMap(jsonString);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
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
		for(String para : getParameters()){
//			logger.info("para: {},{}", para,  rawData.get(para));
			getFormData().put(para, rawData.get(para));
		}
	}
	
	private static String buildJson(String prefix,  String jsonString) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");

//		logger.info("split : {}", jsonString.split("\\{")[1]);
		
		for (String aa : jsonString.split("\\{")) {
//			logger.info("buffer: {}", aa);
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
	
	public static String scrap(String url, String optUrl, Map<String, String> formData, int timeout){
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
			rst = rst.split("\\[")[1].split("\\]")[0];
//			return rst.split(element+ "\"\\s*:")[1];
			return "["+rst+"]";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
