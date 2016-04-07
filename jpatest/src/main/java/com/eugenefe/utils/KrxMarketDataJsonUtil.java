package com.eugenefe.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class KrxMarketDataJsonUtil {
	private final static Logger logger = LoggerFactory.getLogger(KrxMarketDataJsonUtil.class);
	
	public static <E> List<E> convertTo(Class<E> klass, String jsonString){
		return convertTo(klass, jsonString, "block1");
	}	
	
	public static <E> List<E> convertTo(Class<E> klass, String jsonString, String rootParser){
		List<E> rst = new ArrayList<E>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			JsonNode resultNode = mapper.readTree(jsonString);
			String pureJson =resultNode.path(rootParser).toString();
			logger.info("List Size :{}", pureJson);
			
			rst = mapper.readValue(pureJson, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rst;
	}	
	
	/**
	 * method for performance with specific Object. maybe  10 times shorter then using generic form method 
	*/
	public static List<OdsKrxOptionPriceAuto> convertToOdsKrxOptionPriceAuto(String jsonString){
		List<OdsKrxOptionPriceAuto> rst = new ArrayList<OdsKrxOptionPriceAuto>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			JsonNode resultNode = mapper.readTree(jsonString);
			String pureJson =resultNode.path("block1").toString();
			
			rst = Arrays.asList(mapper.readValue(pureJson, OdsKrxOptionPriceAuto[].class));
			 
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rst;
	}	
	

}
