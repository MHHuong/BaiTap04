<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<!doctype html>
<html lang="vi">
<head>
    <!-- title lấy từ <title> của trang con -->
    <title><sitemesh:write property="title" default="BaiTap04"/></title>

    <!-- head (các <link>, <meta>, <script> thêm ở trang con) -->
    <sitemesh:write property="head"/>

    <link rel="stylesheet" href="<%=ctx%>/css/Index.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/topbar/topbar.jsp"/>

<main class="container">
    <!-- body chính của trang con -->
    <sitemesh:write property="body"/>
</main>
</body>
</html>
