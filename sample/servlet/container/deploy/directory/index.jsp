<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<% String ctx = request.getContextPath(); %>

<!-- Title này sẽ được <sitemesh:write property="title"/> đọc -->
<title>Trang chủ - BaiTap04</title>

<!-- Muốn đẩy thêm gì vào <head> tổng thì cứ đặt ở đây -->
<meta name="page.subtitle" content="Trang chủ demo">
<link rel="preconnect" href="https://fonts.gstatic.com">

<section class="card">
    <h2>Chào mừng đến với BaiTap04</h2>
    <div class="actions">
        <a class="btn-primary" href="<c:url value='/login'/>">Đăng nhập</a>
        <a class="btn-secondary" href="<c:url value='/category/list'/>">Xem danh mục</a>
    </div>
</section>
