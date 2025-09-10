<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Topbar.css"/>
<nav class="topbar">
    <div class="left"><a class="brand" href="${pageContext.request.contextPath}/">BaiTap04</a> <a
            href="${pageContext.request.contextPath}/user/home">User Home</a> <a
            href="${pageContext.request.contextPath}/manager/home">Manager Home</a> <a
            href="${pageContext.request.contextPath}/admin/home">Admin Home</a> <a
            href="${pageContext.request.contextPath}/category/list">My Categories</a></div>
    <div class="right">
        <a class="btn ghost" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></div>
</nav>