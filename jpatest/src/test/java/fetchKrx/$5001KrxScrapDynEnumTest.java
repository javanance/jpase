package fetchKrx;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;

public class $5001KrxScrapDynEnumTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	// private final static Logger _logger = LoggerFactory.getLogger("EUGENE");

	public static void main(String[] args)  {
		System.out.println("aaa");
//		dynamicEnumKrxScrapperTest();
	}

	private static void dynamicEnumKrxScrapperTest() {
		try {
			for (EDynKrxScrapper aa : EDynKrxScrapper.values()) {
				if (aa.getName().equals("Krx40020T1")) {
					// logger.info("EDynamicKrxScrap:{},{}", aa.getName(),
					// aa.getParameterData());
					logger.info("EDynamicKrxScrap:{},{}", aa.getName(), EFilePath.KRX_DATA.getFilePath() + aa.getName());
					FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + aa.getName() + ".json", aa.getListJson());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
