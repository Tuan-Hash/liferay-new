package com.hiop.hisc.common.utils;

import java.io.IOException;

import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class ApacheUtil {

	public static String getContent(Request request) throws Exception {
		try {
			ClassicHttpResponse response = (ClassicHttpResponse) request.execute().returnResponse();

			HttpEntity entity = response.getEntity();

			Content content = new Content(
					EntityUtils.toByteArray(entity),
					ContentType.parse(entity.getContentType()));

			return content.asString();
		} catch (IOException e) {
			throw new Exception("Internal server error");
		}

	}

}
