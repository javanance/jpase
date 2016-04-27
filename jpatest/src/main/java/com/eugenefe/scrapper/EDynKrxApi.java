package com.eugenefe.scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
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
	private Map<String, Object> formData = new HashMap<String, Object>();
	private Map<String, String> parameterMeta = new HashMap<String, String>();
	private static Map<String, String> proxyMap = new HashMap<String, String>();
	private static List<String> proxyHost = new ArrayList<String>();
	private static List<Integer> proxyPort = new ArrayList<Integer>();
	private static List<EDynKrxApi> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EDynKrxApi.class);
		proxyMap.put("210.101.131.231", "8088");
		proxyMap.put("211.110.127.210", "3128");
		proxyMap.put("219.255.197.90", "3128");
		proxyMap.put("218.38.54.58", "80");
		proxyMap.put("49.1.244.139", "3128");
		proxyMap.put("210.101.131.231", "8080");
		
		proxyHost.add("50.30.34.30");  proxyPort.add(8888);
		proxyHost.add("108.61.207.52");  proxyPort.add(3128);

		proxyHost.add("115.68.0.28"); proxyPort.add(80);
		proxyHost.add("211.110.127.210"); proxyPort.add(3128);
		proxyHost.add("219.255.197.90"); proxyPort.add(3128);
		proxyHost.add("211.44.183.97"); proxyPort.add(3128);
		proxyHost.add("49.1.244.139");  proxyPort.add(3128);
		
		proxyHost.add("43.226.162.107");  proxyPort.add(8000);
		proxyHost.add("43.226.162.107");  proxyPort.add(8080);
		proxyHost.add("43.226.162.107");  proxyPort.add(80);
		
		
		
		
		
		
		
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
	public Map<String, Object> getFormData(){
		if(formData.size() > 0){
			return formData;
		}
		else{ 
//			formData = getParameterMeta();
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

	public List<Map<String, Object>> getParameterData()  {
//		List<Map<String, String>> temp = new ArrayList<Map<String,String>>();
//		Map<String, String>  tempMap =  new HashMap<String, String>();
//		Map<String, String>  tempMap1 =  new HashMap<String, String>();
//		tempMap.put("isuSrtCd", "000250");
//		tempMap1.put("isuSrtCd", "000440");
//		temp.add(tempMap1);
//		temp.add(tempMap);
//		return temp;
		try {
			String filePath =EFilePath.KRX_DATA.getFilePath() + getParamFile();
			String jsonString = FileUtil.readFile(filePath);
//			logger.info("file : {}", jsonString);
			return JsonUtil.convertToMap(jsonString);
		} catch (Exception e) {
//			TODO:
		}
		return null;
	}
	
	public String getListJson() {
		int timeout = 1000000;
		int cnt =1;
		int idx =0;
		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost.get(0), proxyPort.get(0)));;
		StringBuffer rst = new StringBuffer();
		List<Map<String, Object>> parameterList = getParameterData();
		
		if(parameterList==null || parameterList.size()==0){
			rst.append("{");
			rst.append("\"name\":\"").append(getName()).append("\",");
			rst.append("\"param\":{");
//			logger.info("null ParmaList : {}",  parameterList);
//        	logger.info("null param result : {}",  rst.toString());
        	rst.append("},");
        	rst.append("\"result\":").append(scrap(getUrl(), timeout));
			rst.append("}");
        }
        else{
        	for(Map<String, Object> data : parameterList){
        		if(cnt % 10 == 1){
//					System.setProperty("https.proxyHost", proxyHost.get(idx));
//					System.setProperty("https.proxyPort", proxyPort.get(idx).toString());
					proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost.get(idx), proxyPort.get(idx)));
					
					idx =idx+1;
					idx = idx % 10;
					logger.info("cnt : {},{}" , cnt, idx);
					logger.info("proxy : {},{}" , proxy.type(), proxy.address());
			}
			cnt =cnt +1;
        		filterParameters(data);
        		
    			rst.append("{");
        		rst.append("\"name\":\"").append(getName()).append("\",");
        		rst.append("\"param\":{");
        

				for(Map.Entry<String, Object> entry : getFormData().entrySet()){
					rst.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
				}
				if(rst.lastIndexOf(",") > 0){
					rst.deleteCharAt(rst.lastIndexOf(","));
				}
				rst.append("},");
				rst.append("\"result\":").append(scrap(StrSubstitutor.replace(getUrl(),data), proxy));
//				rst.append("\"result\":").append(scrap(StrSubstitutor.replace(getUrl(),data), timeout));
				
				rst.append("},");
//				rst.append("\"results\":[").append(scrap(StrSubstitutor.replace(getUrl(),data), timeout));
//				rst.append("]},");

				
        	}
        	if(rst.lastIndexOf(",") > 0){
        		rst.deleteCharAt(rst.lastIndexOf(","));
        	}
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
	
	private void filterParameters(Map<String, Object> rawData){
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
	
	public String scrap(String url, Proxy proxy){
		String rst =null;
		try {
			URL website = new URL(url);
			enableSSLSocket();
			
			HttpURLConnection httpUrlConnetion = (HttpURLConnection) website.openConnection(proxy);
			httpUrlConnetion.connect();
			
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			logger.info("response :  {},{}", response.headers(), response.cookies());
			logger.info("responseCode :  {},{}", response.statusCode(), response.header("X-RateLimit-Remaining"));
			rst = response.body();
			
//			String line = null;
//		    StringBuffer tmp = new StringBuffer();
//		    BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnetion.getInputStream()));
//		    while ((line = in.readLine()) != null) {
//		      tmp.append(line);
//		    }
//		    
//		    logger.info("response :  {},{}", response.header("X-RateLimit-Reset"), response.header("X-RateLimit-Remaining"));
//		    Document rstDoc = Jsoup.parse(String.valueOf(tmp));
//		    rst = rstDoc.select("body").text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst;
	}
	public String scrap(String url, int timeout){
		
		String rst =null;
		int cnt =1;
		try {
//			Document rstDoc = Jsoup.connect(url).ignoreContentType(true).timeout(timeout).get();
//			Document rstDoc = Jsoup.connect(url).ignoreContentType(true).get();
//			rst = rstDoc.select("body").text();
//			Response response = Jsoup.connect(url).ignoreContentType(true).cookie("JSESSIONID", "").execute();
			
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			logger.info("response :  {},{}", response.headers(), response.cookies());
			logger.info("responseCode:  {},{}", response.statusCode(), response.header("X-RateLimit-Remaining"));
			
			switch (response.statusCode()) {
            case 200:
                rst = response.body();
                break;
            default:
                
                break;
			}
			return rst.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

	public static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
 
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
}
