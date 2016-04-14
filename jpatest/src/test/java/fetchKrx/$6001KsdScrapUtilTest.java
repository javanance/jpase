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
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.enums.EKsdMenuDyn;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.KsdScrapUtil;
import com.eugenefe.utils.KsdScrapUtilEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $6001KsdScrapUtilTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($6001KsdScrapUtilTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($6001KsdScrapUtilTest.class);
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
		StringBuffer buffer  = new StringBuffer();
		boolean rightType =false;
		// KsdScrapper zz = new KsdScrapper();

		Map<String, String> rawData = new HashMap<String, String>();
		rawData.put("yahoo_id", yahooId);
		rawData.put("ISSUCO_CUSTNO", issucoCustno);
		rawData.put("STD_DT", baseDate);
		rawData.put("ISIN", isin);
		rawData.put("END_PAGE", endPage);
		rawData.put("RED_DT1", baseDate);
		rawData.put("RED_DT2", baseDate);
		
		logger.info("list : {}, {}", EKsdMenu.Ksd200T3.getReferer(), EKsdMenu.Ksd193C3.getPayload());
		
		
		/*try {
			BufferedReader reader = new BufferedReader(	new InputStreamReader(JsonDynaEnum.class.getResourceAsStream("/EKsdMenuDyn.json")));
			
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				if(line.startsWith("[")){
					rightType = true;
				}
				if(rightType){
					line = line.replaceFirst("#.*", "").trim();
//					line = line.replaceFirst("\\/.*", "");
//					line =line.replaceAll("\\s*", "");
					line =line.replaceAll("\\t*", "");
//					line =line.replaceAll("\\n*", "");
					logger.info("line : {},{}",line.indexOf('\t'), line );
		    		if (line.equals("")) {
		    			continue;
		    		}
		    		else{
		    			buffer.append(line);
		    		}
				}
			}
		}catch(IOException ex){
			
		}*/
			
			
		for(EKsdMenuDyn aa : EKsdMenuDyn.values()){
//			logger.info("list : {}, {}", aa.getName(), aa.getPayload());
			rst = KsdScrapUtil.getListJson(aa, rawData);
			logger.info("rst : {}, {}", aa.getName(), rst);
		}
		
//		logger.info("list : {}, {}", EKsdMenu.Ksd193C3.getReferer(), EKsdMenu.Ksd193C3.getProperties());
		
//		logger.info("list : {}, {}", EKsdMenu.Ksd193C3.getReferer(), rst);

//		List<Ksd200T3> zzz = KsdScrapUtilEnum.convertTo(EKsdMenu.Ksd200T3, rawData);
//		for (Ksd200T3 aa : zzz) {
//			logger.info("list : {}, {}", aa.getClass(), aa.getIsin());
//		}
	}
}
