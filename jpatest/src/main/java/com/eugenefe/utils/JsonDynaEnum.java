package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

//public class JsonDynaEnum extends DynaEnum<JsonDynaEnum> {
public class JsonDynaEnum  {
//	private final static Logger logger = LoggerFactory.getLogger(JsonDynaEnum.class);
	private final static Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

	public static <E> List<E> convertTo(Class<E> klass) {
		List<E> rst = new ArrayList<E>();
		String jsonFileName =  klass.getName().substring(klass.getName().lastIndexOf('.')+1);
		jsonFileName = "/" + jsonFileName + ".json";
//		logger.info("json: {},{}", jsonFileName, klass.getName());
		return convertTo(klass, jsonFileName);
		
	}
	public static <E> List<E> convertTo(Class<E> klass, String jsonFileName)  {
		StringBuffer buffer = new StringBuffer();
		boolean rightType = false;

		try {
			BufferedReader reader = new BufferedReader(	new InputStreamReader(JsonDynaEnum.class.getResourceAsStream(jsonFileName)));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				if(line.startsWith("[")){
					rightType = true;
				}
				if(rightType){
					line = line.replaceFirst("#.*", "").trim();
//					line = line.replaceFirst("\\/.*", "");
//					line =line.replaceAll("\\s*", "");
					line =line.replaceAll("\\t*", "");
					line =line.replaceAll("\\n*", "");
//					logger.info("line : {},{}",line.indexOf('\t'), line );
		    		if (line.equals("")) {
		    			continue;
		    		}
		    		else{
		    			buffer.append(line);
		    		}
				}
			}
			logger.info("json : {}", buffer.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			return mapper.readValue(buffer.toString(), mapper.getTypeFactory().constructCollectionType(List.class, klass));
		}catch (JsonMappingException e) {
			logger.error("Dynamic Enum Error : {}, {}", klass.getName(), jsonFileName);
		
		}catch(ExceptionInInitializerError ex){
			logger.error("Dynamic Enum Error : {}, {}", klass.getName(), jsonFileName);
		}
		catch (IOException e) {
			logger.error("Dynamic Enum Error : {}, {}", klass.getName(), jsonFileName);
			e.printStackTrace();
		}	
		return new ArrayList<E>();
	}
}
