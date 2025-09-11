<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${ctx}/">BaiTap04</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nv">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="nv">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="${ctx}/category/list">Category List</a></li>
            </ul>

            <ul class="navbar-nav ms-auto">
                <c:choose>
                    <c:when test="${not empty sessionScope.ACCOUNT}">
                        <li class="nav-item d-flex align-items-center me-2">
                            <img src="${empty sessionScope.ACCOUNT.avatarUrl ? (ctx.concat('/assets/img/avatars/default.png')) : sessionScope.ACCOUNT.avatarUrl}"
                                 alt="avatar" style="width:32px;height:32px;border-radius:50%;object-fit:cover;margin-right:8px"/>
                            <a class="nav-link" href="${ctx}/profile"><strong>${sessionScope.ACCOUNT.fullname}</strong></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${ctx}/logout" title="Đăng xuất">&#x21AA; Logout</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="${ctx}/login">Đăng nhập</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
