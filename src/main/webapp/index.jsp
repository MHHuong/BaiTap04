<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - BaiTap04</title>
    <link rel="stylesheet" href="<%= ctx %>/css/Index.css">
</head>
<body>

<!-- TOPBAR (JSP include) -->
<jsp:include page="/WEB-INF/views/topbar/topbar.jsp"/>

<!-- NỘI DUNG CHÍNH -->
<main class="hero">
    <div class="grid">
        <section class="card">
            <h2>Chào mừng đến với BaiTap04</h2>
            <p>Trang chủ mẫu dùng **chỉ JSP**: link nội bộ dựa trên <code>request.getContextPath()</code>.</p>

            <div class="actions">
                <a class="btn-primary" href="<%= ctx %>/login">Đăng nhập</a>
                <a class="btn-secondary" href="<%= ctx %>/category/list">Xem danh mục</a>
            </div>
        </section>

        <aside class="card">
            <h3>Liên kết nhanh</h3>
            <ul class="list">
                <li><a href="<%= ctx %>/">Trang chủ</a></li>
                <li><a href="<%= ctx %>/category/list">Danh sách danh mục</a></li>
                <li><a href="<%= ctx %>/admin/category">Quản trị danh mục</a></li>
                <li><a href="<%= ctx %>/about">Giới thiệu</a></li>
            </ul>
        </aside>
    </div>
</main>

</body>
</html>
