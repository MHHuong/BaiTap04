<%@ page import="vn.host.constant.Constant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    Object account = (session != null) ? session.getAttribute(Constant.SESSION_ACCOUNT) : null;
%>

<header class="topbar">
    <div class="container">
        <a class="brand" href="<%= ctx %>/">BaiTap04</a>

        <nav class="nav">
            <a href="<%= ctx %>/">Trang chủ</a>
            <a href="<%= ctx %>/category/list">Danh mục</a>
            <a href="<%= ctx %>/about">Giới thiệu</a>
        </nav>

        <div class="auth">
            <%
                if (account != null) {
            %>
            <span>Xin chào!</span>
            <a class="btn" href="<%= ctx %>/logout">Đăng xuất</a>
            <%
            } else {
            %>
            <a class="btn" href="<%= ctx %>/login">Đăng nhập</a>
            <%
                }
            %>
        </div>
    </div>
</header>
