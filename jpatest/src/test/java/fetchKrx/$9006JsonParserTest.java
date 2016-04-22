package fetchKrx;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.JsonParser;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class $9006JsonParserTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($9006JsonParserTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($9006JsonParserTest.class);
	private static Properties properties = new Properties();
	// private static String filePath = "D:\\Dev\\krxData\\";
	private static String filePath;
	private static File folder;
	private static EntityManager em;
	private static OdsKrxMeta meta;

	public static void main(String[] args) throws Exception {
		parser1();
//		parserList();
	}
	private static void parser1(){
		String listString="";
		String json ="";
		JsonParser parser = new JsonParser();
		List<JsonParser> parserList = new ArrayList<JsonParser>();
		try {
			listString = FileUtil.readFile(EFilePath.KRX_DATA.getFilePath() + "stockMaster.json");
			json = listString;
			logger.info("file : {},{}", json, listString );	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(listString.split("^\\s*\\{").length > 1){
			json = listString.replaceFirst("^\\s*\\{", "[{") + "]";
			logger.info("json : {}", json);
		}
//		try {
//			logger.info("aaaa : {}, {}", JsonParser.class.getDeclaredField("name").isAccessible());
//			Field field =JsonParser.class.getDeclaredField("name");
//			field.setAccessible(true);
//			field.set(parser, "KKK");
//			logger.info("aaaa : {}, {}", JsonParser.class.getDeclaredField("name").isAccessible());
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			try {
//				parser = mapper.readValue(json, JsonParser.class);
//				parserList.add(parser);
//			} catch (JsonMappingException jsonEx) {
//			}
			parserList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, JsonParser.class));
//			return parserrser;
		} catch (IOException e) {
				e.printStackTrace();
		}
		logger.info("aa : {}", parserList.get(1).getResult().toString());
		logger.info("aa : {}", JsonUtil.getFieldValue(parserList.get(1).getResult().toString(), "isuCd"));
	}	
	
	private static void parser(){
		try {
			String json = FileUtil.readFile(EFilePath.KRX_DATA.getFilePath() + "stockMaster.json");
			logger.info("file : {},{}", EFilePath.KRX_DATA.getFilePath(), json );	
		} catch (Exception e) {
			// TODO: handle exception
		}
		String listString  =" {\"name\": \"aaa\", \"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\": \"bbb\"}, {\"cc\":\"dd\"}]}";
		String listString1  =" [{\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\": \"bbb\"}, {\"cc\":\"dd\"}]}]";
		JsonParser parser = new JsonParser();
		List<JsonParser> parserList = new ArrayList<JsonParser>();
		String aaa ="";
		
		if(listString.split("^\\s*\\{").length > 1){
		  aaa = listString.replaceFirst("^\\s*\\{", "[{") + "]";
		}
		try {
//			Field.setAccessible(JsonParser.class.getDeclaredFields(), true);
			logger.info("aaaa : {}, {}", JsonParser.class.getDeclaredField("name").isAccessible());
//			JsonParser.class.getDeclaredField("name").set(parser, "zzz");
			Field field =JsonParser.class.getDeclaredField("name");
//			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			field.set(parser, "KKK");
			logger.info("aaaaaaaaa : {}, {}", aaa, parser.getName());
			logger.info("aaaa : {}, {}", JsonParser.class.getDeclaredField("name").isAccessible());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			try {
				parser = mapper.readValue(listString1, JsonParser.class);
				parserList.add(parser);
			} catch (JsonMappingException jsonEx) {
				parserList = mapper.readValue(listString1, mapper.getTypeFactory().constructCollectionType(List.class, JsonParser.class));
			}
//			return parserrser;
		} catch (IOException e) {
				e.printStackTrace();
		}
		logger.info("aa : {}", parserList.get(0));
	}
	
	
	private static void parserEntity(){
		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"aaa\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]";
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
//		String bbb ="  [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
		String ccc  =" [ {\"aaa\":\"kkk\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}]";
//		String aaa  =" {\"isin\":\"kkk\", \"map\" :[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}] }";
		String listString  =" {\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [\"aaa\", \"bbb\", \"cc\",\"dd\"]}";
		String listString1  =" {\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\": \"bbb\"}, {\"cc\":\"dd\"}]}";
//		String aaa  =" {\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"aaa\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]}";
		JsonParser parser = new JsonParser();
		try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
					parser = mapper.readValue(listString1, JsonParser.class);
					
//				rst = mapper.readValue(aaa, mapper.getTypeFactory().constructCollectionType(List.class, klass));
//				return parserrser;
		
		} catch (IOException e) {
				e.printStackTrace();
		}
		logger.info("aa: {},{}", parser.getParam(), parser.getResults());
		
	}
	private static void parserList(){
		String listString  =" {\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\": \"bbb\"}, {\"cc\":\"dd\"}]}";
		String listString1  =" [{\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\": \"bbb\"}, {\"cc\":\"dd\"}]}]";
//		String aaa  =" {\"param\":{\"aa\":\"bb\", \"aaa\":\"ddd\"}, \"result\" : [{\"aaa\":\"bbb\", \"a\":[{\"aa\":\"bb\"}, {\"cc\":\"dd\"}]}, {\"aaa\":\"ddd\", \"b\":{\"aa\":\"bb\", \"cc\":\"dd\"}}]}";
		List<JsonParser> parser = new ArrayList<JsonParser>();
		try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
				
				parser = mapper.readValue(listString, mapper.getTypeFactory().constructCollectionType(List.class, JsonParser.class));
//				parser = mapper.readValue(listString1, JsonParser.class);
//				return parserrser;
		} catch (IOException e) {
				e.printStackTrace();
		}
//		logger.info("aa: {},{}", parser.get(0).getParam(), parser.get(0).getResult());
		
	}
}
