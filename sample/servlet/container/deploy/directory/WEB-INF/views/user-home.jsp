<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">

<jsp:include page="/WEB-INF/views/topbar/topbar.jsp"/>

<div class="layout">
    <%--    <c:if test="${role == 1 || role == 3}">--%>
    <%--        <jsp:include page="/WEB-INF/views/category/form.jsp">--%>
    <%--            <jsp:param name="mode" value="create"/>--%>
    <%--        </jsp:include>--%>
    <%--    </c:if>--%>

    <jsp:include page="/WEB-INF/views/category/list.jsp">
        <jsp:param name="title" value="Tất cả Category"/>
    </jsp:include>
</div>
