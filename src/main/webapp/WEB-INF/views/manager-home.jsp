<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">

<div class="layout">
    <%-- Manager: chỉ xem, KHÔNG hiển thị form create --%>
    <jsp:include page="/WEB-INF/views/category/list.jsp">
        <jsp:param name="title" value="Category của tôi (Manager)"/>
    </jsp:include>
</div>