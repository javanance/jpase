package fetchKrx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.omg.PortableServer.ForwardRequestHelper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.eugenefe.dao.OdsKrxMarketDataDao;
import com.eugenefe.dao.OdsKrxOptionPriceAutoDao;
import com.eugenefe.dao.OdsKrxScrapToFile;
import com.eugenefe.entity.OdsKrxFuturesPriceAuto;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.entity.OdsKrxOptionFinder;
import com.eugenefe.entity.OdsKrxOptionPrice;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.entity.OdsYahooPriceAuto;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.KrxMarketDataFetch;
import com.eugenefe.utils.KrxMarketDataJsonUtil;
import com.eugenefe.utils.KrxMarketDataScrapUtil;
import com.eugenefe.utils.YahooDataJsonUtil;
import com.eugenefe.utils.YahooDataScrapUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $7001YahooDataTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($7001YahooDataTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($7001YahooDataTest.class);
	private static Properties properties = new Properties();
//	private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static  EntityManager em ;
	private static OdsKrxMeta meta;
//	private static OdsKrxOptionPriceAutoDao dao = new OdsKrxOptionPriceAutoDao();
//	private static OdsKrxMarketDataDao daoAll = new OdsKrxMarketDataDao();
	
	public static void main(String[] args) throws Exception{
//		String  isin ="KR4201L42470";
//		String  isin ="KR4201KC2424";
		String  isin ="KR4101L60003";
		String yahooId ="005930.KS";
		String toDate = "2015-04-20";
//		DateTimeTest();
		
//		String url = "http://finance.yahoo.com/q/hp?s=005930.KS+Historical+Prices";
//		String url  ="https://finance.yahoo.com/q/hp?s=005930.KS&a=00&b=4&c=2000&d=02&e=25&f=2015&g=d";
//		
//		String jsonString = YahooDataScrapUtil.scrap(url);
//		logger.info("Yahoo: {}" ,YahooDataScrapUtil.scrap(url));
////		List<OdsYahooPriceAuto> rst = YahooDataJsonUtil.convertTo(OdsYahooPriceAuto.class, jsonString);
//		List<OdsYahooPriceAuto> rst = YahooDataScrapUtil.scrap1(url,1000000);
//		logger.info("list : {}, {}", rst.size(), YahooDataJsonUtil.toString(rst));
//		for( OdsYahooPriceAuto aa : rst){
//			logger.info("list : {},{}", aa.getBaseDate(), aa.getClosePrice());
//		}
//		
		logger.info("sss: {}", YahooDataScrapUtil.scrap(yahooId, toDate, "d"));
	}
}
