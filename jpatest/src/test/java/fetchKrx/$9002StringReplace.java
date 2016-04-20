package fetchKrx;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.utils.JsonStringUtilOld;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class $9002StringReplace {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9002StringReplace.class);
	private final static org.greyhawk.logger.Logger _logger = org.greyhawk.logger.LoggerFactory.getLogger($9002StringReplace.class);
	private static Properties properties = new Properties();
//	private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static  EntityManager em ;
	private static OdsKrxMeta meta;
//	private static OdsKrxOptionPriceAutoDao dao = new OdsKrxOptionPriceAutoDao();
//	private static OdsKrxMarketDataDao daoAll = new OdsKrxMarketDataDao();
	
	public static void main(String[] args) throws Exception{
//		StringReplace();
//		StringAt();
		splitTest1();
//		jacksonReadTree();
		
	}
	private static void jacksonReadTree(){
		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"ccc\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]";
//		String bbb ="  {\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			JsonNode rootNode = mapper.readTree(bbb);
			logger.info("size : {},{}",	rootNode.getNodeType(), rootNode.toString());
			JsonNode firstNode = rootNode.get(0);
			logger.info("size : {},{}",	rootNode.toString(),firstNode.fieldNames());	
			
			Iterator<String> itr = firstNode.fieldNames();
			while(itr.hasNext()){
					logger.info("aa: {}",itr.next());
				
			}
//			for(String  zz : aa.fieldNames()){
//				
//			}
//			logger.info("node : {}", aa.fieldNames());
			
			
//			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void splitTest1(){
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"ccc\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]";
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
		String bbb ="  {\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}";
//		logger.info("aaa : {}" ,bbb.split("[^:]\\s*\\["));
//		logger.info("aaa : {}" ,bbb.split("^\\s*\\[")[1]);
		
//		if(listJsonString.split("[^:]\\s*\\[").length > 2){
//			return listJsonString.split("[^:]\\s*[")[1].split("(,{|\\]")[0];
//		}
		logger.info("entityJson : {}" ,JsonUtil.extractEntityJson(bbb));
		logger.info("element : {}" ,JsonUtil.extractElemntsFrom(bbb));
		
		
	}
	private static void splitTest(){
		String aaa = "<SHOTN_ISIN value=\"${SHOTN_ISIN}\"/><ISIN value=\"${ISIN}\"/> ";
		for(String zz : aaa.split("/>")){
			for(String xx : zz.split("value\\s*=")){
				logger.info("split:  {},{}", zz, xx);
				
			}
		}
		
		String bbb ="[{aaa:bbb, a:{aa:bb, cc:dd}}, {ccc:ddd, b:{aa:bb, cc:dd}}]";
//		String bbb ="[{aaa:bbb, a:[{aa:bb}, {cc:dd}]}, {ccc:ddd, a:[{aa:bb}, {cc:dd}]}]";
		String rep = "{kkk: ddd, zzz: ppp}";
//		bbb.replaceAll("(,|\\[)\\s*\\{", rep.replace("}",  ","));
//		logger.info("bbb : {}" ,bbb.replaceAll("(,|\\[)\\s*\\{", rep.replace("{",  ",{").replace("}",  ",")).replaceFirst(",", "["));
		logger.info("bbb : {}" ,bbb.replaceAll("[^:\\s*\\{]\\s*\\{", rep.replace("}",  ",")));
		logger.info("bbb1 : {}" ,bbb.replaceAll("(,|\\[)\\s*\\{", rep.replace("}",  ",")));
//		for(String zzz :  bbb.split("(,|\\[)\\s*\\{")){
//		}
		logger.info("merge: {}",JsonStringUtilOld.merge(rep, bbb));
	}
	
	private static void StringAt(){
		String aaa = "aaaaabd=db";
		logger.info("aaa: {}", aaa.indexOf("="));
	}
	private static String StringReplace(){
//		String template = "Welcome ${username}!  Your last login was ${lastlogin}";
//		String template = "Welcome \"${username}\"!  Your last login was \"${lastlogin}\"";
		
		String template ="<reqParam action=\"${action}\" task=\"${task}\">"
					+ "<SHOTN_ISIN value=\"${SHOTN_ISIN}\"/>"
					+ "<ISIN value=\"${ISIN}\"/> "
					+ "<MENU_NO value=\"${MENU_NO}\"/> "
					+ "<CMM_BTN_ABBR_NM value=\"${CMM_BTN_ABBR_NM}\"/>"
					+ "<W2XPATH value=\"${W2XPATH}\"/> "
					+ "<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/> "
					+ "<STD_DT value=\"${STD_DT}\"/> "
					+ "<ic_end value=\"${ic_end}\"/> "
					+ "<ic_start value=\"${ic_start}\"/> "
					+ "<ROWNUM value=\"${ROWNUM}\"/> "
					+ "<START_PAGE value=\"${START_PAGE}\"/> "
					+ "<END_PAGE value=\"${END_PAGE}\"/>"
					+ "<FNA value=\"${FNA}\"/>"
					+ "<FNA_TYPE value=\"${FNA_TYPE}\"/> "
					+ "<SETACC_YYMM1 value=\"${SETACC_YYMM1}\"/> "
					+ "<SETACC_YYMM2 value=\"${SETACC_YYMM2}\"/> "
					+ "<SETACC_YYMM3 value=\"${SETACC_YYMM3}\"/> "
					+ "</reqParam>";
		
				 
		Map<String, String> data = new HashMap<String, String>();
		data.put("action", "stkIsueList");
		data.put("task", "ksd.safe.bip.cnts.Stock.process.SecnInfoPTask");
		data.put("SHOTN_ISIN", "009150");
		data.put("ISIN", "KR7009150004");
		data.put("MENU_NO", "44");
		data.put("CMM_BTN_ABBR_NM", "allview,allview,print,hwp,word,pdf,searchIcon,seach,favorites float_left,item float_left,more2,more2,more2,more2,more2 m_left5,more2,more2,more2,more2,link,link,wide,wide,top,");
		data.put("W2XPATH", "/IPORTAL/user/stock/BIP_CNTS02006V.xml");
		data.put("ISSUCO_CUSTNO", "915");
		data.put("STD_DT", "20160331");
		data.put("ic_end", "20160331");
		data.put("ic_start", "20150331");
		data.put("ROWNUM", "5");
		data.put("START_PAGE", "1");
		data.put("END_PAGE", "10");
		data.put("FNA", "60");
		data.put("FNA_TYPE", "별도");
		data.put("SETACC_YYMM1", "2013");
		data.put("SETACC_YYMM2", "2014");
		data.put("SETACC_YYMM3", "2015");
		data.put("zzz", "2015");
		
		
		String rst = StrSubstitutor.replace(template, data);
		logger.info("aa: {}", rst);
		return rst;
	}

}
