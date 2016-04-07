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

public class YahooDataScrapUtil {
	private final static Logger logger = LoggerFactory.getLogger(YahooDataScrapUtil.class);

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
		String yahooId;
		StringBuffer strBuffer = new StringBuffer();

		try {

			Document rstDoc = Jsoup.connect(url).timeout(timeout).get();

			List<Element> tables = rstDoc.select("table.yfnc_datamodoutline1 table tr");

			yahooId = rstDoc.title().split(" ")[0];
			// logger.info("Element: {},{}", rstDoc.title().split(" ")[0],
			// rstDoc.title().split(" ")[1] );
			strBuffer.append("[");
			int cnt = 1;

			NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);
			LocalDate parsedDate;

			for (Element aa : tables) {
				if (aa.childNodeSize() == 7 && !aa.child(0).text().equals("Date")) {
					parsedDate = LocalDate.parse(aa.child(0).text(), formatter);

					strBuffer.append("{");
					strBuffer.append("\"yahoo_id\":\"").append(yahooId).append("\"");
					// strBuffer.append(",\"base_date\":\"").append(aa.child(0).text()).append("\"");
					strBuffer.append(",\"base_date\":\"")
							.append(parsedDate.format(DateTimeFormatter.BASIC_ISO_DATE).toString()).append("\"");
					strBuffer.append(",\"open_price\":").append(aa.child(1).text().replace(",", ""));
					strBuffer.append(",\"high_price\":").append(aa.child(2).text().replace(",", ""));
					strBuffer.append(",\"low_price\":").append(aa.child(3).text().replace(",", ""));
					strBuffer.append(",\"close_price\":").append(aa.child(4).text().replace(",", ""));
					strBuffer.append(",\"volume\":").append(aa.child(5).text().replace(",", ""));
					strBuffer.append(",\"adj_close\":").append(aa.child(6).text().replace(",", ""));
					strBuffer.append("},");
					cnt = cnt + 1;
				}
			}
			strBuffer.deleteCharAt(strBuffer.lastIndexOf(","));
			strBuffer.append("]");
			rst = strBuffer.toString();
			// logger.info("rst :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}

	public static List<OdsYahooPriceAuto> scrap1(String url, int timeout) {
		String rst = null;
		String yahooId;
		List<OdsYahooPriceAuto> rstEntity = new ArrayList<OdsYahooPriceAuto>();
		OdsYahooPriceAuto tempEntity;
		LocalDate parsedDate;

		try {

			Document rstDoc = Jsoup.connect(url).timeout(timeout).get();

			List<Element> tables = rstDoc.select("table.yfnc_datamodoutline1 table tr");

			yahooId = rstDoc.title().split(" ")[0];

			NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US);

			try {

				for (Element aa : tables) {
					tempEntity = new OdsYahooPriceAuto();

//					 logger.info("Element: {},{}", aa);
					if (aa.childNodeSize() == 7 && !aa.child(0).text().equals("Date")) {

						tempEntity.setYahooId(yahooId);
//						tempEntity.setBaseDate(aa.child(0).text());
						tempEntity.setBaseDate(LocalDate.parse(aa.child(0).text(), formatter).format(DateTimeFormatter.BASIC_ISO_DATE).toString());
//						tempEntity.setBaseDate(LocalDate.parse(aa.child(0).text(), formatter).toString());
						tempEntity.setOpenPrice(new BigDecimal(nf.parse(aa.child(1).text()).toString()));
						tempEntity.setHighPrice(new BigDecimal(nf.parse(aa.child(2).text()).toString()));
						tempEntity.setLowPrice(new BigDecimal(nf.parse(aa.child(3).text()).toString()));
						tempEntity.setClosePrice(new BigDecimal(nf.parse(aa.child(4).text()).toString()));
						tempEntity.setVolume(new BigDecimal(nf.parse(aa.child(5).text()).toString()));
						tempEntity.setAdjClose(new BigDecimal(nf.parse(aa.child(6).text()).toString()));

						rstEntity.add(tempEntity);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rstEntity;
	}
}