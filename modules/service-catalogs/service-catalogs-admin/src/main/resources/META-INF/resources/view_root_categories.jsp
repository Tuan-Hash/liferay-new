<%@ include file="/init.jsp" %>

  <portlet:actionURL var="submitURL" name="cloneCatalogs">
  </portlet:actionURL>

  <portlet:actionURL var="fetchCatalogsURL" name="fetchCatalogs">
  </portlet:actionURL>

  <div class="hisc-root-categories-management">
    <liferay-ui:error key="error500" message="Internal server error" />
    <div id="<portlet:namespace />-root"></div>
  </div>

  <script type="text/javascript">
    ServiceCatalog.default(
      '<portlet:namespace />-root',
      {
        namespace: '<portlet:namespace />',
        submitURL: '${submitURL}',
        fetchCatalogsURL: '${fetchCatalogsURL}',
        initData: '${initData}'
      });
  </script>

<script type="text/javascript">
  document.addEventListener('readystatechange', event => {
    switch (document.readyState) {
      case "interactive":
        // The document is interactive.
        document.querySelector("body").style.visibility = "hidden";
        break;
      case "complete":
        setTimeout(() => {
          document.querySelector("body").classList.remove('cadmin');
          
          setTimeout(() => {
            document.querySelector("body").style.visibility = "visible";
          }, 500);
        }, 1000);
        break;
      }
    }
  );
</script>