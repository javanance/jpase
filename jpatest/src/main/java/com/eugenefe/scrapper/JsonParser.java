package com.eugenefe.scrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonParser {
	private String name;
	private Map<String, JsonNode>	param ;
	private List<JsonNode> results;
	private JsonNode result;
	public JsonParser() {
	}
	
	
	public String getName() {
		return name;
	}

	public Map<String, JsonNode> getParam() {
		return param;
	}
	
	
	public JsonNode getResult() {
		return result;
	}

	@JsonIgnoreProperties
	public List<JsonNode> getResults() {
		return results;
	}

	

}
