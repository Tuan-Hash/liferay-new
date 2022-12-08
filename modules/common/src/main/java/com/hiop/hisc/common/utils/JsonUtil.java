package com.hiop.hisc.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static JsonNode getDataNode(String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(jsonStr);
		return root.get("data");
	}

	public static String getResultsString(JsonNode dataNode) {
		return dataNode.get("results").toString();
	}

	public static JsonNode getNode(String jsonStr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(jsonStr);
	}

	public static String getValueString(JsonNode dataNode) {
		return dataNode.get("value").toString();
	}

}
