<%--
  Created by IntelliJ IDEA.
  User: MAI HONG TIN
  Date: 9/9/2025
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2>Category của tôi</h2>
<a href="${pageContext.request.contextPath}/category/new">+ Thêm mới</a>
<table border="1">
    <tr>
        <th>#</th>
        <th>Name</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="c" items="${cateList}" varStatus="st">
        <tr>
            <td>${st.index+1}</td>
            <td>${c.categoryname}</td>
            <td>${c.status}</td>
            <td>
                <a href="${pageContext.request.contextPath}/category/edit?id=${c.categoryid}">Sửa</a> |
                <a href="${pageContext.request.contextPath}/category/delete?id=${c.categoryid}"
                   onclick="return confirm('Xóa?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
