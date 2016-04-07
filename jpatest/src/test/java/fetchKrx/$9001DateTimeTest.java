package fetchKrx;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.eugenefe.entity.OdsKrxMeta;

public class $9001DateTimeTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9001DateTimeTest.class);
	private final static org.greyhawk.logger.Logger _logger = org.greyhawk.logger.LoggerFactory.getLogger($9001DateTimeTest.class);
	private static Properties properties = new Properties();
//	private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static  EntityManager em ;
	private static OdsKrxMeta meta;
//	private static OdsKrxOptionPriceAutoDao dao = new OdsKrxOptionPriceAutoDao();
//	private static OdsKrxMarketDataDao daoAll = new OdsKrxMarketDataDao();
	
	public static void main(String[] args) throws Exception{
		DateTimeTest();
	}
	
	private static  void DateTimeTest(){
//		String str = "Jun 03, 2003";
		String str = "19860408";
//		String str = "1986-04-08";
//		date.now(ZoneId.of("America/Chicago"));
		
		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.UK);
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
			
			LocalDate parsedDate = LocalDate.parse(str, DateTimeFormatter.BASIC_ISO_DATE);
//			LocalDate parsedDate = LocalDate.parse(str, formatter);
			
//			LocalDate parsedDate = LocalDate.now();
			
//			LocalDate parsedDate = LocalDate.parse(str);
//			date only!!!!
			logger.info("To String      : {},{}", str, parsedDate.toString());
			logger.info("of Pattern      : {},{}", str, parsedDate.format(formatter));
			logger.info("BASIC_ISO_DATE      : {},{}", str, parsedDate.format(DateTimeFormatter.BASIC_ISO_DATE));
			logger.info("ISO_DATE            : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_DATE));
			logger.info("ISO_LOCAL_DATE      : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
			logger.info("ISO_ORDINAL_DATE	 : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_ORDINAL_DATE));
			logger.info("ISO_WEEK_DATE		 	: {},{}", str, parsedDate.format(DateTimeFormatter.ISO_WEEK_DATE));
//			logger.info("ISO_INSTANT         : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_INSTANT));
//			logger.info("ISO_DATE_TIME       : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_DATE_TIME));
//			logger.info("ISO_LOCAL_TIME      : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_LOCAL_TIME));
//			logger.info("ISO_OFFSET_DATE     : {},{}", str, parsedDate.format(DateTimeFormatter.ISO_OFFSET_DATE));
//			logger.info("ISO_OFFSET_DATE_TIME: {},{}", str, parsedDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
//			logger.info("ISO_ZONED_DATE_TIME	: {},{}", str, parsedDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
			
			_logger.info("aa: %d, %d, %s", parsedDate.getDayOfYear(), parsedDate.getDayOfMonth(), parsedDate.getDayOfWeek());
			_logger.info("aa: {},{},{}", parsedDate.getDayOfYear(), parsedDate.getDayOfMonth(), parsedDate.getDayOfWeek());
			
			
		}catch(Exception ex){
			
			logger.info("ex:{}");
			
		}
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
//		DateTimeFormatter.
	}
	
	
}
