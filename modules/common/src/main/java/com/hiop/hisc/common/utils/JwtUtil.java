package com.hiop.hisc.common.utils;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiop.hisc.common.dto.AccessToken;
import com.hiop.hisc.common.dto.Jwt;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectWebKeys;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.service.OpenIdConnectSessionLocalServiceUtil;

public class JwtUtil {

  public static Jwt getJWToken(HttpServletRequest httpServletRequest) {
    try {

      HttpServletRequest httpRequest = PortalUtil.getOriginalServletRequest(httpServletRequest);
      HttpSession httpSession = httpRequest.getSession(false);

      if (httpSession == null) {
        return null;
      }

      long openIdConnectSessionId = (long) httpSession
          .getAttribute(OpenIdConnectWebKeys.OPEN_ID_CONNECT_SESSION_ID);

      OpenIdConnectSession openIdConnectSession = OpenIdConnectSessionLocalServiceUtil
          .getOpenIdConnectSession(openIdConnectSessionId);

      JsonNode dataNode = new ObjectMapper().readTree(openIdConnectSession.getAccessToken());
      Jwt jwToken = new ObjectMapper().readValue(dataNode.traverse(), Jwt.class);

      return jwToken;
    } catch (Exception e) {
      _log.error("JwtUtil.getJWToken.error", e);
      return null;
    }
  }

  public static AccessToken decodeAccessToken(String accessTokenStr) {
    AccessToken accessToken = new AccessToken();

    try {
      // split out the "parts" (header, payload and signature)
      String[] parts = accessTokenStr.split("\\.");

      String headerJson = decode(parts[0]);
      String payloadJson = decode(parts[1]);
      String signatureJson = decode(parts[2]);

      accessToken.setHeader(new ObjectMapper().readTree(headerJson));
      accessToken.setPayload(new ObjectMapper().readTree(payloadJson));
      accessToken.setSignature(signatureJson);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return accessToken;
  }

  public static String setHeaderAuthorization(String accessToken) {
    return "Bearer " + accessToken;
  }

  public static String decode(String encodedStr) {
    return new String(Base64.getUrlDecoder().decode(encodedStr));
  }

  private static final Log _log = LogFactoryUtil.getLog(JwtUtil.class);

}
