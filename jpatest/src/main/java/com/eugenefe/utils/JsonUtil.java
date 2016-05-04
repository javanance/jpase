package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.reflections.Reflections;

import com.eugenefe.entity.Ksd200T2;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.enums.EKsdMenu;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.mchange.util.IteratorUtils;

public class JsonUtil {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JsonUtil.class);
	
	
	/**
	 * @param listJsonString
	 * @return
	 * return  JsonNode.element() for Array Type otherwise return itself as List
	 */
	public static List<JsonNode> getEntityNodes(String listJsonString){
		List<JsonNode> list = new ArrayList<JsonNode>();
		JsonNode  jsonNode = getRootNode(listJsonString);
		if(jsonNode ==null){
			return null;
		}
		switch (jsonNode.getNodeType()) {
		case ARRAY:
			Iterator<JsonNode> itr =jsonNode.iterator(); 
			while (itr.hasNext()) {
				list.add(itr.next());
		    }
			break;
		default:
			list.add(jsonNode);
		}
		
		return list;
	}
	
	
	public static List<String> getElementNames(String listJsonString){
		List<String> rst = new ArrayList<String>();
		JsonNode firstNode = getEntityNodes(listJsonString).get(0);
		
		Iterator<String> itr = firstNode.fieldNames();
		while(itr.hasNext()){
			rst.add(itr.next());
		}
		return rst;
	}
	public static JsonNode getElement(String listJsonString, String elementName){
		JsonNode firstNode = getEntityNodes(listJsonString).get(0);
		return firstNode.findValue(elementName);
	}
	
	public static JsonNode getElements(String listJsonString, String elementName){
		ArrayNode rst = JsonNodeFactory.instance.arrayNode();
		JsonNode elementNode ;
		
		for (JsonNode jsonNode : getEntityNodes(listJsonString)) {
//			logger.info("ele : {}", jsonNode.findValue(elementName));
			elementNode = jsonNode.findValue(elementName);
			switch (elementNode.getNodeType()) {
			case ARRAY:
				for(JsonNode node : getEntityNodes(elementNode.toString())){
					rst.add(node);
				}
				break;
			default:
				rst.add(elementNode);
				break;
			}
		}
		return rst;
	}
	

	
//	public static String getElementValues(String listJsonString, String elementName){
////	public static List<String> getElementValues(String listJsonString, String elementName){
////		List<String> rst = new ArrayList<String>();
//		ArrayNode rst = JsonNodeFactory.instance.arrayNode();
//		JsonNode elementNode ;
//		for (JsonNode jsonNode : getEntityNodes(listJsonString)) {
//			logger.info("ele : {}", jsonNode.findValue(elementName));
//			elementNode = jsonNode.findValue(elementName);
//			switch (elementNode.getNodeType()) {
//			case ARRAY:
//				for(JsonNode node : getE)
//				break;
//
//			default:
//				break;
//			}
//			rst.add(jsonNode.findValue(elementName));
//		}
//		return rst.toString();
//	}
	
	public static String addList(String listJsonString, String addedListJsonString){
		List<JsonNode> rst = new ArrayList<JsonNode>();
		rst = getEntityNodes(listJsonString);
		if(rst ==null || rst.size()==0){
			return getEntityNodes(addedListJsonString).toString();
		}else{
			rst = getEntityNodes(listJsonString);
			rst.addAll(getEntityNodes(addedListJsonString));
//			logger.info("rst : {}", rst);
			return rst.toString();
		}
	}
	
	public static String attachElement(String entityJson, String attachedJson, String elementName){
		ArrayNode  arrResult = JsonNodeFactory.instance.arrayNode();
		ObjectNode  objResult = JsonNodeFactory.instance.objectNode();
		ObjectNode  temp ;
		JsonNode baseElement = getRootNode(entityJson);
		JsonNode attachedElement = getRootNode(attachedJson);

		switch (baseElement.getNodeType()) {
		case ARRAY :	
			objResult = (ObjectNode)baseElement.get(0);
			break;
//		case POJO:
//			break;
		case OBJECT:
			objResult = (ObjectNode)baseElement;
			break;
			
		default:
			break;
		}
		
		switch (attachedElement.getNodeType()) {
		case OBJECT:
			if(elementName == null || elementName =="" || elementName.toLowerCase().equals("root")){
				for(String element : JsonUtil.getElementNames(attachedElement.toString())){
					objResult.set(element,JsonUtil.getElement(attachedElement.toString(), element));
				}
			}else{
				objResult.set(elementName, attachedElement);
			}
			 return objResult.toString();
		case ARRAY :
			for (final JsonNode objNode : attachedElement) {
				if(elementName == null || elementName =="" || elementName.toLowerCase().equals("root")){
					temp = objResult.deepCopy();
					
					for(String element : JsonUtil.getElementNames(objNode.toString())){
//						logger.info("attach: {},{}", element, JsonUtil.getElement(objNode.toString(), element));
//						temp = JsonNodeFactory.instance.objectNode();
						temp.set(element,JsonUtil.getElement(objNode.toString(), element));
					}
					arrResult.add(temp);
				}	
				else{
					temp = JsonNodeFactory.instance.objectNode();
					temp = objResult.deepCopy();
					arrResult.add(objResult.deepCopy().set(elementName,objNode));
				}
			}
			return arrResult.toString();
		default:
			break;
		}
		return null;
	}
	public static String attachElement(String entityJson, String attachedJson){
		return attachElement(entityJson, attachedJson, "");
	}


	public static String convertToJson(Map<String, String> map){
		String rst ="";
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			rst = mapper.writeValueAsString(map);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  rst;
	}
	
	public static List<Map<String, Object>> convertToMap(String listJsonString){
		List<Map<String, Object>> rst = new ArrayList<Map<String,Object>>();
		if(listJsonString.trim().startsWith("{")){
			listJsonString = "["+listJsonString + "]";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			rst = mapper.readValue(listJsonString, new ArrayList<HashMap<String, String>>().getClass());
			rst = mapper.readValue(listJsonString, new ArrayList<Map<String, Object>>().getClass());
			
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	public static List<Map<String, String>> convertToStringMap(String listJsonString){
		List<Map<String, String>> rst = new ArrayList<Map<String,String>>();
		if(listJsonString.trim().startsWith("{")){
			listJsonString = "["+listJsonString + "]";
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			rst = mapper.readValue(listJsonString, new ArrayList<HashMap<String, String>>().getClass());
			rst = mapper.readValue(listJsonString, new ArrayList<Map<String, String>>().getClass());
			
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
	
	public static <E> List<E> convertTo(Class<E> klass, String jsonString) {
		List<E> rst = new ArrayList<E>();
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
//*****************private method ***************
	
	private static JsonNode getRootNode(String listJsonString){
		if(listJsonString== null|| listJsonString=="" ){
			return null;
		}
		try {
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			JsonNode rootNode = mapper.readTree(listJsonString);
			JsonNode rootNode = getJsonMapper().readTree(listJsonString);
			return rootNode;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ObjectMapper getJsonMapper(){
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}

	private static ObjectMapper getJsonMapper(PropertyNamingStrategy nameStratege){
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(nameStratege);
		return mapper;
	}
}
