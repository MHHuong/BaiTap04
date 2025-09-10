<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ListCategory.css">
<div class="card">
    <div class="card-title">
        <h3 style="margin:0">${title}</h3>

    </div>
    <c:if test="${role == 3 || role == 1}">
        <a class="btn" href="${pageContext.request.contextPath}/category/create">+ Thêm mới</a>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Status</th>
            <th style="width:220px">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${cateList}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${c.categoryname}</td>
                <td>${c.status}</td>
                <td>
                    <c:choose>
                        <c:when test="${role == 1}">
                            <a href="${pageContext.request.contextPath}/category/edit?id=${c.categoryid}">Sửa</a>
                            |
                            <form action="${pageContext.request.contextPath}/category/delete" method="post"
                                  style="display:inline">
                                <input type="hidden" name="id" value="${c.categoryid}"/>
                                <button type="submit" onclick="return confirm('Xóa?')">Xóa</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${not empty c.owner and c.owner.userid == me}">
                                <a href="${pageContext.request.contextPath}/category/edit?id=${c.categoryid}">Sửa</a>
                                |
                                <form action="${pageContext.request.contextPath}/category/delete" method="post"
                                      style="display:inline">
                                    <input type="hidden" name="id" value="${c.categoryid}"/>
                                    <button type="submit" onclick="return confirm('Xóa?')">Xóa</button>
                                </form>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
