package fetchKrx;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;

import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.utils.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $9004JsonMapperlTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9004JsonMapperlTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($9004JsonMapperlTest.class);
	private static Properties properties = new Properties();
	 private static String filePath = "D:\\Dev\\ksdData\\Ksd200T3.json";
//	private static String filePath;
	private static File folder;
	private static EntityManager em;
	private static OdsKrxMeta meta;

	public static void main(String[] args) throws Exception {
		
		try {
			
			String jsonString = FileUtil.readFile(filePath);
			jsonString = "[{\"isin\":\"KR6534386596\",\"kor_secn_nm\":\"WTI CRUDE OIL FUTURES\",\"isin\":\"KR6534386597\",\"xrc_std_ratio\":90,\"xrc_stdprc\":41.13,\"scmnt_content\":\"1-2차\"}]";
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			List<Ksd200T2> list = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Ksd200T2.class));
			for(Ksd200T2 aa : list){
				logger.info("aaa: {}", aa.getIsin());
			}
			
//			List<Ksd200T3> list = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Ksd200T3.class));
//			for(Ksd200T3 aa : list){
//				logger.info("aaa: {}", aa.getIsin());
//			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
