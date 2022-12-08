package com.hiop.hisc.workflow.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private JsonUtil() {
  }

  public static JsonNode getDataNode(String jsonStr)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(jsonStr);
    return root.get("data");
  }

  public static String getResultsString(JsonNode dataNode) {
    return dataNode.get("results").toString();
  }
}
