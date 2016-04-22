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
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.collect.Lists;
import com.mchange.util.IteratorUtils;

public class JsonUtil {
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JsonUtil.class);
	
	public static List<JsonNode> getElements(String listJsonString){
		List<JsonNode> list = new ArrayList<JsonNode>();
		JsonNode  jsonNode = getRootNode(listJsonString);
		
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
	
	
	public static List<String> getFieldNames(String listJsonString){
		List<String> rst = new ArrayList<String>();
		JsonNode firstNode = getElements(listJsonString).get(0);
		
		Iterator<String> itr = firstNode.fieldNames();
		while(itr.hasNext()){
			rst.add(itr.next());
		}
		return rst;
		
	}
	
	public static String getFieldValue(String listJsonString, String filedName){
		JsonNode node = getElements(listJsonString).get(0);
		return node.findValue(filedName).toString();
	}
	
	public static List<String> getFieldValues(String listJsonString, String filedName){
		List<String> rst = new ArrayList<String>();
		for (JsonNode jsonNode : getElements(listJsonString)) {
			rst.add(jsonNode.findValue(filedName).toString());
		}
		return rst;
	}
	
	public static String addList(String listJsonString, String addedListJsonString){
		List<JsonNode> rst = getElements(listJsonString);
		rst.addAll(getElements(addedListJsonString));
		return rst.toString();
	}
	
	public static List<Map<String, JsonNode>> getMapElemnts(String listJsonString){
		List<Map<String, JsonNode>> rst = new ArrayList<Map<String, JsonNode>>();
		for(JsonNode node : getElements(listJsonString)){
			rst.add(getFieds(node));
		}
		return rst;
	}
	
	
	public static String join(String entityJsonString, String listJsonString){
		List<Map<String, JsonNode>> rst = new ArrayList<Map<String, JsonNode>>();
		Map<String, JsonNode> before = getFieds(getElements(entityJsonString).get(0));

		for(JsonNode node :getElements(listJsonString)){
			Map<String, JsonNode> temp = new HashMap<String, JsonNode>();
			temp.putAll(before);
			temp.putAll(getFieds(node));
			rst.add(temp);
		}
		
		return rst.toString();
	}
	
	public static String merge(String entityJsonString, String listJsonString){
		StringBuffer buffer = new StringBuffer();
		List<Map<String, String>> rst  = convertToMap(listJsonString);
		Map<String, String> temp ;
		Map<String, String> addedMap =  convertToMap(changeToListJson(entityJsonString)).get(0);

		for(Map<String,String> map :  convertToMap(listJsonString)){
			temp = new HashMap<String, String>();
			temp.putAll(addedMap);
			temp.putAll(map);
			rst.add(temp);
			buffer.append(convertToEntityJson(temp)).append(",");
		}
		if(buffer.lastIndexOf(",") > 0){
			buffer.deleteCharAt(buffer.lastIndexOf(","));
		}
		return changeToListJson(buffer.toString());
	}
	
	public static String convertToEntityJson(Map<String, String> map){
		String rst ="";
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			rst = mapper.writeValueAsString(map);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  rst;
	}
	
	public static String convertToListJson(Map<String, String> map){
//		return "["+ convertToEntityJson(map)+"]";
		return changeToListJson(convertToEntityJson(map));
	}
	
	
	
	public static List<Map<String, String>> convertToMap(String listJsonString){
		List<Map<String, String>> rst = new ArrayList<Map<String,String>>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
//			rst = mapper.readValue(listJsonString, new ArrayList<HashMap<String, String>>().getClass());
			rst = mapper.readValue(listJsonString, new ArrayList<Map<String, String>>().getClass());
			
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
//	public static Map<String, String> convertToMap1(String listJsonString){
//		Map<String, String> rst = new HashMap<String,String>();
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
////			rst = mapper.readValue(listJsonString, new ArrayList<HashMap<String, String>>().getClass());
//			rst = mapper.readValue(listJsonString, new HashMap<String, String>().getClass());
//			
//			return rst;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return rst;
//	}
	
	public static <E> List<E> convertTo(Class<E> klass, String jsonString) {
		List<E> rst = new ArrayList<E>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);

			rst = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rst;
	}
	
//*****************private method ***************
	private static String changeToListJson(String  entityJson){
		if(entityJson.trim().startsWith("[")){
			return entityJson;
		}
		return "[" + entityJson + "]";
	}
	
	private static JsonNode getFirstNode(String listJsonString){
		JsonNode rootNode = getRootNode(listJsonString) ;
		switch (rootNode.getNodeType()) {
		case ARRAY:
			return rootNode.get(0);
		case OBJECT:
		case POJO:	
		default:
			return rootNode;
		}
	}
	
	private static Map<String, JsonNode> getFieds(JsonNode jsonNode ){
		Map<String, JsonNode>  rst = new HashMap<String, JsonNode>();
		Iterator<Entry<String ,JsonNode>> itr = jsonNode.fields();
		while (itr.hasNext()) {
			Entry<String, JsonNode> temp = itr.next();
			rst.put(temp.getKey(), temp.getValue());
		}
		return rst;
	}
	
	private static JsonNode getRootNode(String listJsonString){
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			JsonNode rootNode = mapper.readTree(listJsonString);
			return rootNode;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
//	*******************odl**************
	public static String extractEntityJson(String listJsonString) {
		JsonNode  jsonNode = getFirstNode(listJsonString);
		if(jsonNode==null){
			return listJsonString;
		}else {
			return jsonNode.toString();
		}
	}
	
	public static String addListOld(String listJsonString, String addedListJsonString){
		 if(listJsonString ==null || listJsonString ==""){
			 return addedListJsonString;
		 }else if(addedListJsonString ==null || addedListJsonString ==""){
			 return listJsonString;
		 }else{
			 if(getFieldNames(listJsonString).equals(getFieldNames(addedListJsonString))){
				 String addedJson = listJsonString + addedListJsonString;
				 return addedJson.replaceFirst("\\]\\s*\\[", ",");
			 }
			 else{
				 logger.warn("Two Json String don't have the same element. Skip to add second list");
				 return listJsonString;
			 }
		}
	}
	
	
	public static String addList1(String listJsonString, String addedListJsonString){
		String rst;
		StringBuffer buffer = new StringBuffer();
		List<Map<String, String>> list = JsonUtil.convertToMap(listJsonString);
		list.addAll(JsonUtil.convertToMap(addedListJsonString));
		for(Map<String, String> aa : list){
			buffer.append(convertToEntityJson(aa)).append(",");
		}
		buffer.deleteCharAt(buffer.lastIndexOf(","));
		return "["+ buffer.toString()+"]";
		
	}

	public static String mergeOld(String entityJsonString, String listJsonString){
		if(entityJsonString.length() > 0){
			return listJsonString.replaceAll("\\s*\\{", entityJsonString.replace("}",  ","));
		}
		else{ 
			return listJsonString;
		}
	}
//	public static List<String> extractElemntsFrom(String jsonString){
//		List<String> rst = new ArrayList<String>();
//		JsonNode firstNode = getFirstNode(jsonString);
//		
//		Iterator<String> itr = firstNode.fieldNames();
//		while(itr.hasNext()){
//			rst.add(itr.next());
//		}
//		return rst;
//	}
}
