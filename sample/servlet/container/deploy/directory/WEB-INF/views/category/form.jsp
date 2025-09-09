<%--
  Created by IntelliJ IDEA.
  User: MAI HONG TIN
  Date: 9/9/2025
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2><c:choose><c:when test="${not empty item}">Sửa</c:when><c:otherwise>Thêm</c:otherwise></c:choose> Category</h2>
<form method="post"
      action="${pageContext.request.contextPath}/category/<c:out value='${empty item ? "create" : "update"}'/>">
    <c:if test="${not empty item}">
        <input type="hidden" name="categoryid" value="${item.categoryid}"/>
    </c:if>
    <div>
        <label>Tên</label>
        <input name="categoryname" value="${item.categoryname}"/>
    </div>
    <div>
        <label>Ảnh (url)</label>
        <input name="images" value="${item.images}"/>
    </div>
    <div>
        <label>Status</label>
        <input type="number" name="status" value="${empty item ? 1 : item.status}"/>
    </div>
    <button>Lưu</button>
</form>
