package fetchKrx;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxScrapper3;
import com.eugenefe.utils.FileUtil;

public class $5003KrxScrapDynEnumTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	// private final static Logger _logger = LoggerFactory.getLogger("EUGENE");

	public static void main(String[] args)  {
//		System.out.println("aaa");
		dailyPriceTest();
	}

	private static void dailyPriceTest() {
		try {
			for (EDynKrxScrapper3 aa : EDynKrxScrapper3.values()) {
				if (aa.getName().contains("Krx100")) {
//				if (aa.getName().equals("Krx80079T1")) {	
					logger.info("EDynamicKrxScrap:{},{}", aa.getName(),  aa.getParameterData());
					logger.info("EDynamicKrxScrap:{},{}", aa.getParameterMeta(), EFilePath.KRX_DATA.getFilePath() + aa.getName());
					FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + aa.getName() + ".json", aa.getJson());
//					logger.info("EDynamicKrxScrap:{},{}", aa.getName(), aa.getJson());
				}
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	private static void others() {
		try {
			for (EDynKrxScrapper3 aa : EDynKrxScrapper3.values()) {
				if (aa.getName().equals("Krx80079T1")) {	
					logger.info("EDynamicKrxScrap:{},{}", aa.getName(),  aa.getParameterData());
					logger.info("EDynamicKrxScrap:{},{}", aa.getParameterMeta(), EFilePath.KRX_DATA.getFilePath() + aa.getName());
					FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + aa.getName() + ".json", aa.getJson());
//					logger.info("EDynamicKrxScrap:{},{}", aa.getName(), aa.getJson());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
