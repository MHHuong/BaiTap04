<%--
  Created by IntelliJ IDEA.
  User: MAI HONG TIN
  Date: 9/9/2025
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>User Home - Tất cả Category</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Owner</th>
        <th>Status</th>
    </tr>
    <c:forEach var="c" items="${cateList}">
        <tr>
            <td>${c.categoryid}</td>
            <td>${c.categoryname}</td>
            <td>${c.userid}</td>
            <td>${c.status}</td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/category/list">Quản lý Category của tôi</a>
