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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class YahooDataJsonUtil {
	private final static Logger logger = LoggerFactory.getLogger(YahooDataJsonUtil.class);
	
	
	public static <E> List<E> convertTo(Class<E> klass, String jsonString){
		List<E> rst = new ArrayList<E>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			mapper.enable(DeserializationFeature.);
//			mapper.enabel()
			
			rst = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rst;
	}	
	
	public static<E> String toString(List<E> list){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[");
		try{
			ObjectMapper mapper = new ObjectMapper();
			for( E aa : list){
				strBuffer.append(mapper.writeValueAsString(aa)).append(",");
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("]");		
		}catch(IOException ex){
			
		}
		
		return strBuffer.toString();
	}

}
