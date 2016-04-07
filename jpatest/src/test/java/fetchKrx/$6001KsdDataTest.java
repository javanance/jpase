package fetchKrx;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.jsoup.nodes.Document;

import com.eugenefe.entity.Ksd191T3;
import com.eugenefe.entity.Ksd200P;
import com.eugenefe.entity.Ksd200T1;
import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.Ksd200T3;
import com.eugenefe.entity.Ksd200T5;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EKsdMenu;
import com.eugenefe.scrapper.KsdMenu1913;
import com.eugenefe.scrapper.KsdMenu200T1;
import com.eugenefe.scrapper.KsdMenu200T2;
import com.eugenefe.scrapper.KsdMenu200T3;
import com.eugenefe.scrapper.KsdMenu200T5;
import com.eugenefe.scrapper.KsdScrapper;
import com.eugenefe.scrapper.KsdMenu200P;
import com.eugenefe.utils.KsdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $6001KsdDataTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($6001KsdDataTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($6001KsdDataTest.class);
	private static Properties properties = new Properties();
//	private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static  EntityManager em ;
	private static OdsKrxMeta meta;
	
	public static void main(String[] args) throws Exception{
//		String  isin ="KR4201L42470";
//		String  isin ="KR4201KC2424";
//		String  isin ="KR6723301646";
//		String isin ="KR6523308395";		//elS
		String isin ="KR6534386596";		//DLS
		
		String yahooId ="005930.KS";
		String baseDate = "20150420";
		String issucoCustno ="151";
		String endPage ="200";
		String payload ;
		String prePayload;
		String referer;
		
		Map<String, String> data = new HashMap<String, String>();
		KsdScrapper zz = new KsdScrapper();
		
//		menu193(baseDate);
//		menu200P(issucoCustno);
//		menu200T1(isin);
//		menu200T2(isin);
//		menu200T3(isin);
//		menu200T5(isin);
		for(EKsdMenu menu : EKsdMenu.values()){
			data.clear();
//			logger.info("param: {},{}", menu,   menu.getParameters());
			for(String param: menu.getParameters()){
				if(param.equals("ISIN")){
					data.put(param, isin);
				}
				else if(param.equals("ISSUCO_CUSTNO")){
					data.put(param, issucoCustno);
				}
				else if(param.equals("STD_DT")){
					data.put(param, baseDate);
				}
				else if(param.equals("END_PAGE")){
					data.put(param, endPage);
				}
			}
//			Thread.sleep(1000);
//			TimeUnit.SECONDS.sleep(1);
//			menuscrap(menu, data);
//			logger.info("ListJson:{},{}", menu,zz.getListJson(menu, data));
		}
		data.clear();
		for(String param: EKsdMenu.KSD207T1.getParameters()){
			if(param.equals("ISIN")){
				data.put(param, isin);
			}
			else if(param.equals("ISSUCO_CUSTNO")){
				data.put(param, issucoCustno);
			}
			else if(param.equals("STD_DT")){
				data.put(param, baseDate);
			}
			else if(param.equals("END_PAGE")){
				data.put(param, endPage);
			}
			else if(param.equals("RED_DT1")){
				data.put(param, baseDate);
			}
			else if(param.equals("RED_DT2")){
				data.put(param, baseDate);
			}
		}
		logger.info("ListJson:{}", zz.getListJson(EKsdMenu.KSD207T1, data));
		
//		zzz();
	}
	private static void menu193(String baseDate){
		KsdMenu1913 aa = new KsdMenu1913();
		logger.info("aaa:{}", aa.getDocument(baseDate));
		for(Ksd191T3 bb : aa.getList(baseDate)){
    		logger.info("aa :{}, {}", bb.getFirstIssuQty());
			_logger.info("aa : %s, %s, %s,  %f", bb.getIsin(), bb.getKorSecnNm(), bb.getRepSecnNm(), bb.getFirstIssuQty());
			
		}
	}
	
	private static void menu200T1(String isin){
		KsdMenu200T1 zz = new KsdMenu200T1();
		logger.info("Doc:{}", zz.getDocument(isin));
		logger.info("EntityJson:{}", zz.getEntityJson(isin));
		logger.info("ListJson:{}", zz.getListJson(isin));
		logger.info("Entity :{}, {}", zz.getEntity(isin).getIssuDt());
		for(Ksd200T1 bb : zz.getList(isin)){
			_logger.info("aa : %s, %s, %s", bb.getIsin(), bb.getKorSecnNm(), bb.getKorSecnNm());
		
	    }
	}
	
	private static void menu200T2(String isin){
		KsdMenu200T2 zz = new KsdMenu200T2();
		logger.info("Doc:{}", zz.getDocument(isin));
		logger.info("ListJson:{}", zz.getListJson(isin));
		for(Ksd200T2 bb : zz.getList(isin)){
			_logger.info("aa : %s, %f, %s", bb.getIsin(), bb.getXrcStdRatio(), bb.getKorSecnNm());
		
	    }
	}
	
	private static void menu200T3(String isin){
		KsdMenu200T3 zz = new KsdMenu200T3();
		logger.info("Doc:{}", zz.getDocument(isin));
		logger.info("ListJson:{}", zz.getListJson(isin));
		for(Ksd200T3 bb : zz.getList(isin)){
			_logger.info("aa : %s, %s, %s", bb.getIsin(), bb.getMidValatBeginDt(), bb.getRedFormulaContent());
		
	    }
	}
	
//	private static void menu200T5(String isin){
//		KsdMenu200T5 zz = new KsdMenu200T5();
//		logger.info("Doc:{}", zz.getDocument(isin));
//		logger.info("ListJson:{}", zz.getListJson(isin));
//		for(Ksd200T5 bb : zz.getList(isin)){
//			_logger.info("aa : %s, %s, %f", bb.getIsin(), bb.getUnderlyingId(), bb.getLowerBarrierRatio());
//		
//	    }
//	}
	
	private static void menuscrap(EKsdMenu menu, Map<String, String> data){
		KsdScrapper zz = new KsdScrapper();
		logger.info("ListJson:{}", zz.getListJson(menu, data));
		
		
		List<Ksd200T1> rst = new ArrayList<Ksd200T1>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = Arrays.asList(mapper.readValue(zz.getListJson(menu, data), Ksd200T1[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		logger.info("Doc:{}", zz.getDocument(isin));
//		logger.info("ListJson:{}", zz.getListJson(isin));
		for(Ksd200T1 bb : rst){
			_logger.info("aa : %s, %s, %f", bb.getIsin(), bb.getScmntContent(), bb.getPrcpPrsvRate());
	    }
	}
	
	
	

	
	private static void menu200P(String issucoCustno){
		KsdMenu200P zz = new KsdMenu200P();
//		logger.info("aaa:{}", zz.getDocument(comCode));
		logger.info("aaa:{}", zz.getListJson(issucoCustno));
		for(Ksd200P bb : zz.getList(issucoCustno)){
			logger.info("aa :{}, {}", bb.getIsin());
			_logger.info("aa : %s, %s, %s", bb.getIsin(), bb.getKorSecnNm(), bb.getIssucoCustno());
		
	    }
	}
	
	
	
	private void aaa(String baseDate){
		String payload ;
		String prePayload;
		String referer;
		try {
			properties.load($6001KsdDataTest.class.getResourceAsStream("/ksd.properties"));
			referer = properties.getProperty("[191_3]referer");
			
			prePayload = properties.getProperty("[191_3]prePayload");
//			prePayload = String.format(prePayload, toDate) ;
			Document doc =  KsdUtil.getAAA(referer, String.format(prePayload, baseDate));
			String aa = doc.select("list_cnt").attr("value");
			logger.info("aaaa: {}", aa);
			
			
			payload = properties.getProperty("[191_3]payload");
//			payload = String.format(payload, toDate, 1, doc.select("list_cnt").attr("value"));
			
			logger.info("AAA: {},{} ", referer, payload);
			logger.info("AAA: {}",  KsdUtil.getAAA(referer, String.format(payload, baseDate, 1, doc.select("list_cnt").attr("value"))));
			

		}catch(Exception ex){
			
		}
	}

	private static void zzz(){
		String str = "10.0";
//		String str = "aaa";
		double dbl = Double.parseDouble(str);
		int intval = Integer.parseInt(str);
		logger.info("zzz : {}, {}", dbl, intval);
		
		String className= "com.eugenefe.scrapper.KsdMenu200T1";
		
		try {
			Class c = Class.forName(className);
			Method m[] = c.getDeclaredMethods();
			for(Method aa : m){
				logger.info("method: {}, {}", aa.getGenericReturnType().getTypeName(), aa);
			}
//			for (int i = 0; i < m.length; i++){
//				logger.info("method: {}", m[i]);
//			}
				
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
	