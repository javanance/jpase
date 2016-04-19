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

import org.apache.commons.lang3.text.StrSubstitutor;
import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.reflections.Reflections;

import com.eugenefe.entity.Krx40020C1;
import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.Ksd200T3;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EKrxMenuDyn;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.enums.EKsdMenuDyn;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.KrxScrapUtil;
import com.eugenefe.utils.KsdScrapUtil;
import com.eugenefe.utils.KsdScrapUtilEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $5001KrxScrapUtilTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	// private final static Logger _logger = LoggerFactory.getLogger("EUGENE");
	private static Properties properties = new Properties();
	// private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;

	public static void main(String[] args) throws Exception {
		 dynamicEnumKrxScrapperTest();
	}

	

	private static void dynamicEnumKrxScrapperTest() {
		for (EDynKrxScrapper aa : EDynKrxScrapper.values()) {
			if (aa.getName().equals("Krx40020T1")) {

				// logger.info("EDynamicKrxScrap:{},{}", aa.getName(),
				// aa.getParameterData());
				logger.info("EDynamicKrxScrap:{},{}", aa.getName(), aa.getListJson());
			}
		}

	}
}
