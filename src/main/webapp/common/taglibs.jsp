<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn"%>--%>
<c:set var="ctx_app" value="${pageContext.request.contextPath}"/>
<%--本地--%>
<c:set var="ctx_static" value="${pageContext.request.contextPath}/static"/>
<%--测试--%>
<%--<c:set var="ctx_static" value="http://ecstest2010.10010.com/light-up-5g/static/"/>--%>
<%--生产--%>
<%--<c:set var="ctx_static" value="https://img.client.10010.com/activitys/wheel/light-up-5g/static"/>--%>
