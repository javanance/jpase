package fetchKrx;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;

public class $5003KrxTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
