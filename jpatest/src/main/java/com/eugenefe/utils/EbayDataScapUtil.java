package com.eugenefe.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsYahooPrice;
import com.eugenefe.entity.OdsYahooPriceAuto;

public class EbayDataScapUtil {
	private final static Logger logger = LoggerFactory.getLogger(EbayDataScapUtil.class);

	public static String scrap(String url) {
		int timeout = 100000000;
		return scrap(url, timeout);
	}
	
	public static String scrap(String yahooId, String toDate, String frequency){
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.KOREA);
//		LocalDate parsedDate= LocalDate.parse(toDate, formatter);
		LocalDate parsedDate= LocalDate.parse(toDate);
		return scarp(yahooId, parsedDate, frequency);
	}
	
	public static String scarp(String yahooId, LocalDate toDate, String frequency){
        String url = "https://finance.yahoo.com/q/hp?s=%s&a=00&b=4&c=2000&d=%s&e=%s&f=%s&g=%s";
		
		url =String.format(url, yahooId, toDate.withMonth(toDate.getMonthValue()-1).getMonthValue(), toDate.getDayOfMonth(), toDate.getYear(),  frequency);
		return  scrap(url);
	}
	
	public static String scrap(String url, int timeout) {
		String rst = null;
		StringBuffer strBuffer = new StringBuffer();

		try {

			Document rstDoc = Jsoup.connect(url).timeout(timeout).get();

			
//			List<Element> tables = rstDoc.select("table.yfnc_datamodoutline1 table tr");
//			rst = rstDoc.select("#ResultSetItems").toString();
			rst = rstDoc.select("#ListViewInner").toString();
			
			logger.info("Element: {},{}", rst);
//			 logger.info("Element: {},{}", rstDoc.select("#RightSummaryPanel .mbg-nw"));
			
		
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

	
}