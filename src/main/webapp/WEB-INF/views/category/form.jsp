<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/FormCategory.css">
<div class="card">
    <h3 style="margin-top:0">
        <c:choose>
            <c:when test="${mode == 'edit'}">Sửa Category</c:when>
            <c:otherwise>Thêm Category</c:otherwise>
        </c:choose>
    </h3>

    <form method="post"
          action="${pageContext.request.contextPath}/category/${mode == 'edit' ? 'edit' : 'create'}">
        <c:if test="${mode == 'edit'}">
            <input type="hidden" name="id" value="${category.categoryid}"/>
        </c:if>

        <div class="form-row">
            <label>Tên</label>
            <input name="categoryname" value="${mode == 'edit' ? category.categoryname : ''}"/>
        </div>

        <div class="form-row">
            <label>Ảnh (url)</label>
            <input name="images" value="${mode == 'edit' ? category.images : ''}"/>
        </div>

        <div class="form-row">
            <label>Status</label>
            <input type="number" name="status" value="${mode == 'edit' ? category.status : 1}"/>
        </div>

        <button type="submit" class="btn">${mode == 'edit' ? 'Cập nhật' : 'Thêm mới'}</button>
    </form>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
</div>
