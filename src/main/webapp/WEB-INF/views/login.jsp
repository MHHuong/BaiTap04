<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- SiteMesh sẽ đọc title này -->
<title>Đăng nhập</title>

<section class="card p-4" style="max-width:460px;margin:48px auto;">
    <h3 class="mb-3">Đăng nhập</h3>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post" action="${ctx}/login">
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input class="form-control" name="username" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input class="form-control" type="password" name="password" required>
        </div>
        <button class="btn btn-primary w-100">Đăng nhập</button>
    </form>
</section>
