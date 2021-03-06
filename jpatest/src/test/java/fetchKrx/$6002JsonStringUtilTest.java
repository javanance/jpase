package fetchKrx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.reflections.Reflections;

import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.Ksd200T3;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.enums.ENamingConvention;
import com.eugenefe.utils.JsonStringUtilOld;
import com.eugenefe.utils.KsdScrapUtil;
import com.eugenefe.utils.KsdScrapUtilEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $6002JsonStringUtilTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($6002JsonStringUtilTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($6002JsonStringUtilTest.class);
	private static Properties properties = new Properties();
	// private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static EntityManager em;
	private static OdsKrxMeta meta;

	public static void main(String[] args) throws Exception {
		// String isin ="KR4201L42470";
		// String isin ="KR4201KC2424";
		// String isin ="KR6723301646";
		// String isin ="KR6523308395"; //elS
		String isin = "KR6534386596"; // DLS

		String yahooId = "005930.KS";
		String baseDate = "20150420";
		String issucoCustno = "151";
		String endPage = "200";
		String payload;
		String prePayload;
		String referer;
		String rst;
		// KsdScrapper zz = new KsdScrapper();

		Map<String, String> rawData = new HashMap<String, String>();
		rawData.put("yahoo_id", yahooId);
		rawData.put("ISSUCO_CUSTNO", issucoCustno);
		rawData.put("STD_DT", baseDate);
		rawData.put("ISIN", isin);
		rawData.put("END_PAGE", endPage);
		rawData.put("RED_DT1", baseDate);
		rawData.put("RED_DT2", baseDate);

		rst = KsdScrapUtilEnum.getListJson(EKsdMenu.Ksd200T2, rawData);
//		logger.info("list : {}, {}", rst);

		
//		logger.info("translate : {},{}", JsonStringUtil.getPureJson(rst), JsonStringUtil.extractDbColumnFrom(rst));
		
//		logger.info("translate : {},{}", JsonStringUtil.extractElemntsFrom(rst));
//		logger.info("translate : {},{}", JsonStringUtil.convert2CamelCase(rst));
		String kebab ="aaa-bbb-ccc";
		String pascal = "CccAaaBbb";
		
//		logger.info("aa :{}", ENamingConvention.convertToCamelCase(rst, '_'));
//		logger.info("kebab :{}", ENamingConvention.convertToCamelCase(kebab, '-'));
//		logger.info("snake : {},{}", ENamingConvention.SNAKE_CASE.convertToCamelCase(JsonStringUtil.extractElemntsFrom(rst)));
		logger.info("kebab : {},{}", ENamingConvention.KEBAB_CASE.convertToCamelCase(kebab));
		logger.info("pascal : {},{}", ENamingConvention.PASCAL_CASE.convertToCamelCase(pascal));
	}

	

}
