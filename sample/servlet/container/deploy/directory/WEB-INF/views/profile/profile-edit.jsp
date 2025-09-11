<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="u" value="${sessionScope.ACCOUNT}"/>

<section class="card p-4" style="max-width:720px;margin:32px auto;">
    <h4 class="mb-3">Chỉnh sửa hồ sơ</h4>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${ctx}/profile/edit" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input class="form-control" name="fullname" value="${u.fullname}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input class="form-control" name="phone" value="${u.phone}">
        </div>

        <div class="mb-3">
            <label class="form-label d-block">Ảnh đại diện</label>
            <img src="${empty u.avatarUrl ? (ctx.concat('/assets/img/avatars/default.png')) : u.avatarUrl}"
                 alt="avatar" style="width:96px;height:96px;border-radius:50%;object-fit:cover;"/>
            <input class="form-control mt-2" type="file" name="avatarFile" accept="image/*">
            <div class="form-text">Hỗ trợ jpg, png, webp…</div>
        </div>

        <div class="mb-3">
            <label class="form-label">Hoặc URL ảnh</label>
            <input class="form-control" name="avatarUrl" value="${u.avatarUrl}">
        </div>

        <button class="btn btn-primary">Lưu</button>
        <a class="btn btn-secondary" href="${ctx}/profile">Huỷ</a>
    </form>
</section>
