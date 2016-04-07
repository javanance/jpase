package com.eugenefe.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class KrxMarketDataFetch {
	private final static Logger logger = LoggerFactory.getLogger(KrxMarketDataFetch.class);
	
	public static String scrap(String url, String optUrl, Map<String, String> formData){
		int  timeout = 100000000;
		return scrap(url, optUrl, formData, timeout);
	}
	
	public static String scrap(String url, String optUrl, Map<String, String> formData, int timeout){
		String rst =null;
		try {
			Response opt = Jsoup.connect(optUrl).timeout(timeout).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			logger.info("Opt : {}", optCode);
			
			formData.put("code", optCode);
			Response rstResponse = Jsoup.connect(url)
									 .timeout(timeout)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			rst = rstResponse.parse().select("body").text();
			logger.info("rst :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	public static String scrap(OdsKrxMeta meta, Map<String,  String> formData, int timeout){
		return scrap( meta.getUrl(), meta.getOptUrl(), formData, timeout);
	}
	
	public static String scrap(OdsKrxMeta meta, Map<String,  String> formData){
		int timeout = 100000000;
		return scrap( meta.getUrl(), meta.getOptUrl(), formData, timeout);
	}
	
	
	public static <E> List<E> scrapOld(OdsKrxMeta meta, Map<String,  String> formData, int timeout) {
		List<E> rst = new ArrayList<E>();
		Class kk = rst.getClass().getTypeParameters()[0].getClass();
		logger.info("KKK :{},{}", rst.getClass().getTypeParameters()[0], kk);
		
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).timeout(timeout).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			logger.info("Opt : {}", optCode);
			
			formData.put("code", optCode);


			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(timeout)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
//			mapper.readV
//			rst = mapper.readValue(str2, new TypeReference<List<klass>>(){} );
			rst = mapper.readValue(str2, mapper.getTypeFactory().constructCollectionType(List.class, kk));
//			rst = Arrays.asList(mapper.readValue(str2, aa[].class));
			 
			 
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <E> List<E> scrap(OdsKrxMeta meta, Map<String,  String> formData, Class<E> klass, int timeout) {
		List<E> rst = new ArrayList<E>();
		
		
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).timeout(timeout).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			logger.info("Opt : {}", optCode);
			
			formData.put("code", optCode);


			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(timeout)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
//			mapper.readV
//			rst = mapper.readValue(str2, new TypeReference<List<klass>>(){} );
			rst = mapper.readValue(str2, mapper.getTypeFactory().constructCollectionType(List.class, klass));
//			rst = Arrays.asList(mapper.readValue(str2, aa[].class));
			 
			 
			logger.info("List Size :{}", rst.size());
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <E> List<E> scrap(OdsKrxMeta meta, Map<String,  String> formData, Class<E> klass) {
		List<E> rst = new ArrayList<E>();

		Class kk = rst.getClass().getTypeParameters().getClass();
		logger.info("KKK :{}", kk);
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).timeout(1000000000).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			logger.info("Opt : {}", optCode);
			
			formData.put("code", optCode);


			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(100000000)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
//			mapper.readV
//			rst = mapper.readValue(str2, new TypeReference<List<klass>>(){} );
			rst = mapper.readValue(str2, mapper.getTypeFactory().constructCollectionType(List.class, klass.getClass()));
//			rst = Arrays.asList(mapper.readValue(str2, aa[].class));
			 
			 
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
