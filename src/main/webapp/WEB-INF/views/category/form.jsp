<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleFormCategory.css">
<h2>
    <c:choose>
        <c:when test="${mode == 'edit'}">Sửa</c:when>
        <c:otherwise>Thêm</c:otherwise>
    </c:choose>
    Category
</h2>

<form method="post"
      action="${pageContext.request.contextPath}/category/${mode == 'edit' ? 'edit' : 'create'}">

    <c:if test="${mode == 'edit'}">
        <input type="hidden" name="id" value="${category.categoryid}"/>
    </c:if>

    <div>
        <label>Tên</label>
        <input name="categoryname" value="${mode == 'edit' ? category.categoryname : ''}"/>
    </div>

    <div>
        <label>Ảnh (url)</label>
        <input name="images" value="${mode == 'edit' ? category.images : ''}"/>
    </div>

    <div>
        <label>Status</label>
        <input type="number" name="status" value="${mode == 'edit' ? category.status : 1}"/>
    </div>

    <button type="submit">Lưu</button>
</form>

<c:if test="${not empty error}">
    <div style="color:#c00;margin-top:8px">${error}</div>
</c:if>
