package com.hiop.hisc.reports.web.util;

import java.util.List;

import javax.portlet.PortletRequest;

import com.hiop.hisc.reports.model.Category;
import com.hiop.hisc.reports.model.Report;
import com.hiop.hisc.reports.service.CategoryLocalServiceUtil;
import com.hiop.hisc.reports.service.ReportLocalServiceUtil;
import com.hiop.hisc.reports.web.dto.EmbedConfig;
import com.hiop.hisc.reports.web.service.PowerBIService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;

public class ReportsUtil {
  public static Object getAllReportsJson(long companyId, long groupId) {
    DynamicQuery query = CategoryLocalServiceUtil.dynamicQuery();
    query.add(PropertyFactoryUtil.forName("companyId").eq(companyId));
    query.add(PropertyFactoryUtil.forName("groupId").eq(groupId));
    List<Category> categories = CategoryLocalServiceUtil.dynamicQuery(query);

    JSONArray categoriesJsonArray = JSONFactoryUtil.createJSONArray();
    for (Category category : categories) {
      JSONObject categoryJson = JSONFactoryUtil.createJSONObject();
      categoryJson.put("id", category.getCategoryId());
      categoryJson.put("name", category.getName());

      query = ReportLocalServiceUtil.dynamicQuery();
      query.add(
          PropertyFactoryUtil.forName("primaryKey.categoryId").eq(category.getCategoryId()));
      List<Report> reports = ReportLocalServiceUtil.dynamicQuery(query);
      JSONArray reportsJsonArray = JSONFactoryUtil.createJSONArray();
      for (Report report : reports) {
        JSONObject reportJson = JSONFactoryUtil.createJSONObject();
        reportJson.put("id", report.getReportId());
        reportJson.put("name", report.getName());
        reportsJsonArray.put(reportJson);
      }
      categoryJson.put("reports", reportsJsonArray);

      categoriesJsonArray.put(categoryJson);
    }

    return categoriesJsonArray;
  }

  public static EmbedConfig getEmbedConfig(PortletRequest portletRequest) throws Exception {
    String groupId = ParamUtil.getString(portletRequest, "groupId");
    String reportId = ParamUtil.getString(portletRequest, "reportId");

    return new PowerBIService(portletRequest).getEmbedConfig(groupId, reportId);
  }

}
