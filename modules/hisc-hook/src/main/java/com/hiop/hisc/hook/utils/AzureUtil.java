package com.hiop.hisc.hook.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.dto.AccessToken;
import com.hiop.hisc.common.utils.JwtUtil;
import com.hiop.hisc.hook.dto.azure.AzureUser;

public class AzureUtil {

	public static String getUserId(String accessTokenStr) {
		try {
			AccessToken accessToken = JwtUtil.decodeAccessToken(accessTokenStr);
			AzureUser azureUser = new ObjectMapper().readValue(accessToken.getPayload().traverse(), AzureUser.class);

			return azureUser.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
