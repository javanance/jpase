package jpadbmanu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import com.eugenefe.entity.OdsIsinMaster;
import com.eugenefe.entity.OdsKrx10074;
import com.eugenefe.enums.EFilePath;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

public class $1001OdsKrxTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	// private final static Logger _logger = LoggerFactory.getLogger("EUGENE");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String bssd = "201411";
		String str ;
		OdsIsinMaster aa ; 
		String elementName = "block1";
//		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
//		em.getTransaction().begin();
		List<OdsKrx10074> list = new ArrayList<OdsKrx10074>();
		int cnt = 0;
		String elementJson;
		try {
			logger.info("krx: {}", EFilePath.KRX_DATA.getFilePath());
			String jsonString =FileUtil.readFile(EFilePath.KRX_DATA.getFilePath() + "Krx10074T1.json");
			elementJson = JsonUtil.getElement(jsonString, elementName).toString();
			logger.info("aaa :{}", elementJson);
			list = JsonUtil.convertTo(OdsKrx10074.class, elementJson);
			for(OdsKrx10074 bbb : list){
				logger.info("node : {},{}", bbb.getIsuKorNm(), bbb.getIsuShrtCd());
				
			}
//			for(JsonNode node : JsonUtil.getElements(jsonString, elementName)){
//				logger.info("node : {}", node);
//				
//			}
//				em.merge(aa);
//				cnt = cnt +1;
//				if ( cnt % 50 == 0 ) { //20, same as the JDBC batch size        //flush a batch of inserts and release memory:
//					em.flush();
//					em.clear();
//				}
//			
//			em.getTransaction().commit();
//			em.close();
//			PersistenceManager.INSTANCE.close();
	        

		} catch (IOException ex) {
			logger.info("bbb");
		}

	}

}
