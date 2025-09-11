<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <title><sitemesh:write property="title" default="BaiTap04"/></title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>

    <!-- Head phụ do trang con đẩy lên -->
    <sitemesh:write property="head"/>
</head>
<body>
<!-- TOPBAR dùng session acc để hiện avatar, logout, category, profile -->
<jsp:include page="/WEB-INF/views/topbar/topbar.jsp"/>

<main class="container my-4">
    <!-- NỘI DUNG TRANG CON -->
    <sitemesh:write property="body"/>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
