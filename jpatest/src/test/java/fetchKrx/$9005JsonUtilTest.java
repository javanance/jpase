package fetchKrx;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class $9005JsonUtilTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9005JsonUtilTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($9005JsonUtilTest.class);
	private static Properties properties = new Properties();
	// private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static EntityManager em;
	private static OdsKrxMeta meta;

	public static void main(String[] args) throws Exception {
//		jacksonReadTree();
		splitTest1();
	}
	
	private static void jacksonReadTree(){
		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"ccc\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]";
//		String bbb ="  {\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}";
//		String bbb =  " \"aaa\" : \"false\" ";
		JsonNode firstNode;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			JsonNode rootNode = mapper.readTree(bbb);
			if(rootNode.getNodeType().equals(JsonNodeType.ARRAY)){
				firstNode = rootNode.get(0);
			}
			else{ 
				firstNode = rootNode;
			}
			
			logger.info("root : {},{}",	rootNode.getNodeType(), rootNode.toString());
			logger.info("first : {},{}",	firstNode.getNodeType(), firstNode.toString());
			logger.info("size : {},{}",	rootNode.elements() ,firstNode.fieldNames());	
			
			Iterator<String> itr = firstNode.fieldNames();
			Iterator<JsonNode> jsonitr = firstNode.elements();
			Iterator<Entry<String, JsonNode>> fielditr =firstNode.fields();
			
			while (fielditr.hasNext()) {
				Entry<String, JsonNode> entry = fielditr.next();
			
//				logger.info("en: {}", fielditr.next());
				logger.info("en: {},{}", entry.getKey(), entry.getValue());
				
			}
			while(jsonitr.hasNext()){
				logger.info("element: {}",jsonitr.next());
			
		}
//			firstNode.
			while(itr.hasNext()){
					logger.info("aa: {}",itr.next());
				
			}
			
			for (JsonNode jsonNode : firstNode) {
				logger.info("type : {}",  jsonNode.getNodeType());
				if(jsonNode.getNodeType().equals(JsonNodeType.ARRAY)){
					for (JsonNode jsonNode2 : jsonNode) {
						logger.info("type : {}",  jsonNode2.getNodeType());
					}
				}
				
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
		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"aaa\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]";
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
		String ccc  =" [ {\"aaa\":\"kkk\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
//		String aaa  =" {\"isin\":\"kkk\", \"map\" :[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}] }";
		String aaa  =" {\"aaa\":\"kkk\", \"map\" :\"zz\"}";
		
		Map<String, String > map = new HashMap<String, String>();
		map.put("aaa", "aa");
		map.put("bbb", "bb");
//		logger.info("aaa : {}" ,bbb.split("[^:]\\s*\\["));
//		logger.info("aaa : {}" ,bbb.split("^\\s*\\[")[1]);
		
//		if(listJsonString.split("[^:]\\s*\\[").length > 2){
//			return listJsonString.split("[^:]\\s*[")[1].split("(,{|\\]")[0];
//		}
		logger.info("entityJson : {}",	JsonUtil.extractEntityJson(bbb));
		logger.info("getElement : {}",	JsonUtil.getElements(bbb).get(0));
		
		
//		logger.info("element    : {}",	JsonUtil.extractElemntsFrom(bbb));
		logger.info("fromMap    : {}",  JsonUtil.convertToEntityJson(map));
		logger.info("toMap      : {}",  JsonUtil.convertToMap(bbb));
		logger.info("etyJson    : {}",  JsonUtil.convertToEntityJson(JsonUtil.convertToMap(bbb).get(1)));
		logger.info("listJson   : {}",  JsonUtil.convertToListJson(JsonUtil.convertToMap(bbb).get(1)));
		logger.info("add        : {}",  JsonUtil.addList(bbb, ccc));
		logger.info("add1       : {}", JsonUtil.addList1(bbb, ccc));
		logger.info("addOld     : {}", JsonUtil.addListOld(bbb, ccc));
		logger.info("merge      : {}", JsonUtil.merge(aaa, bbb));
		logger.info("join       : {}", JsonUtil.join(aaa, bbb));
		logger.info("bbb        : {}",bbb);
		logger.info("mergeOld   : {}",  JsonUtil.mergeOld(aaa, bbb));
		
		
	}
}
