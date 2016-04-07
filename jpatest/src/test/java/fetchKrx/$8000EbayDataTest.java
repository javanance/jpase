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

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.omg.PortableServer.ForwardRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.eugenefe.utils.EbayDataScapUtil;
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

public class $8000EbayDataTest {
	private final static Logger logger = LoggerFactory.getLogger($8000EbayDataTest.class);
	private static Properties properties = new Properties();
//	private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static  EntityManager em ;
	private static OdsKrxMeta meta;
//	private static OdsKrxOptionPriceAutoDao dao = new OdsKrxOptionPriceAutoDao();
//	private static OdsKrxMarketDataDao daoAll = new OdsKrxMarketDataDao();
	
	public static void main(String[] args) throws Exception{
		
//		String url = "http://www.ebay.com/itm/200601995403?_trksid=p2055119.m1438.l2649&ssPageName=STRK%3AMEBIDX%3AIT";
		String url = "http://www.ebay.com/itm/ALPHA-RX-2-Iron-set-5-PW-6pc-RH-used-/222016407453?_trksid=p2047675.l2557&ssPageName=STRK%3AMEBIDX%3AIT&nma=true&si=cMKeVIDOqsViOE5dROFbY7Fn%252BDs%253D&orig_cvip=true&rt=nc";
		
		String sellerUrl  ="http://www.ebay.com/usr/glorygolf?_trksid=p2047675.l2559";
		String sellerItemUrl  ="http://www.ebay.com/sch/glorygolf/m.html?_nkw=&_armrs=1&_ipg=&_from=" ;
//		
		String jsonString = EbayDataScapUtil.scrap(url);
//		String jsonString = EbayDataScapUtil.scrap(sellerItemUrl);
		logger.info("Ebay: {}" ,jsonString);
	}
	

}
