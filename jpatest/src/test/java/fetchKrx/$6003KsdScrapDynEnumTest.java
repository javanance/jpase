package fetchKrx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.eugenefe.enums.EFilePath;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.enums.EKsdMenuDyn;
import com.eugenefe.scrapper.EDynKsdScrapper;
import com.eugenefe.scrapper.EDynKsdScrapper2;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.KsdScrapUtil;
import com.eugenefe.utils.KsdScrapUtilEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $6003KsdScrapDynEnumTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($6003KsdScrapDynEnumTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($6003KsdScrapDynEnumTest.class);
	private static Properties properties = new Properties();
	// private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static EntityManager em;
	private static OdsKrxMeta meta;

	public static void main(String[] args) throws Exception {
		
		test();	
//		formTypeTest();	
	}
	private static void test(){
//		try {
			for (EDynKsdScrapper aa : EDynKsdScrapper.values()) {
				if(aa.getName().equals("Ksd200T1")){
				 logger.info("list : {}, {}", aa.getParameters(), aa.getParameterData());
				 logger.info("list : {}, {}",  aa.getListJson());
//				 FileUtil.writeFile(EFilePath.KSD_DATA.getFilePath() + aa.getName() + ".json", aa.getListJson());
				}
			}

//		} catch (IOException e) {
//			 TODO: handle exception
//		}
	}
	private static void formTypeTest(){
		try {
			
		for (EDynKsdScrapper2 aa : EDynKsdScrapper2.values()) {
			if(aa.getName().equals("Ksd191C3")){
			 logger.info("list : {}, {}", aa.getName(), aa.getParameterMeta());
			 logger.info("list : {}, {}",   aa.getParameterData());
			 logger.info("list1 : {}, {}",   aa.getPayload());
			 logger.info("list1 : {}, {}",   aa.getListJson());
			 FileUtil.writeFile(EFilePath.KSD_DATA.getFilePath() + aa.getName() + ".json", aa.getListJson());
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
