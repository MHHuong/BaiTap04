<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleListCategory.css"/>

<h2>Category của tôi</h2>

<c:set var="role" value="${sessionScope.ACCOUNT.roleid}"/>
<c:set var="me" value="${sessionScope.ACCOUNT.userid}"/>
<c:if test="${role == 1 || role == 3}">
    <a class="btn" href="${pageContext.request.contextPath}/category/create">+ Thêm mới</a>
</c:if>
<p style="opacity:.6">debug: role=${role}, me=${me}</p>

<table class="table">
    <thead>
    <tr>
        <th>STT</th>
        <th>Name</th>
        <th>Status</th>
        <th>Actions</th>
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

                    <c:when test="${role == 2}">
                        <span class="muted">Chỉ xem</span>
                    </c:when>

                    <c:otherwise>
                        <c:if test="
                (${c.owner != null and c.owner.userid == me})
              ">
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