package com.eugenefe.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.fasterxml.jackson.databind.JsonNode;

public class KrxMarketDataScrapUtil {
	private final static Logger logger = LoggerFactory.getLogger(KrxMarketDataScrapUtil.class);
	
	public static String scrap(String url, String optUrl, Map<String, String> formData){
		int  timeout = 100000000;
		return scrap(url, optUrl, formData, timeout);
	}
	
	public static String scrap(String url, String optUrl, Map<String, String> formData, int timeout){
		String rst =null;
		try {
			Document optDoc = Jsoup.connect(optUrl).timeout(timeout).get();
			String optCode = optDoc.select("body").text();
			formData.put("code", optCode);

//			logger.info("optCod : {},{}", optCode , formData);

			Document rstDoc = Jsoup.connect(url)
					 			   .timeout(timeout)
					 			   .data(formData)
					 			   .post();
			
			rst = rstDoc.select("body").text();
			
//			logger.info("rst :{}", rst);
			return rst;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	
	


}
