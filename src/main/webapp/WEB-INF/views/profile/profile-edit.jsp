<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="u" value="${sessionScope.acc}"/>

<section class="card p-4" style="max-width:720px;margin:32px auto;">
    <h4 class="mb-3">Chỉnh sửa hồ sơ</h4>

    <form method="post" action="${ctx}/profile/edit" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">Họ tên</label>
            <input class="form-control" name="fullname" value="${u.fullname}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input class="form-control" name="phone" value="${u.phone}">
        </div>

        <c:if test="${not empty u.avatarUrl}">
            <div class="mb-3 d-flex align-items-center">
                <img src="${u.avatarUrl}"
                     alt="avatar hiện tại"
                     style="width:72px;height:72px;border-radius:50%;object-fit:cover;margin-right:12px;">
                <span class="text-muted small">Ảnh hiện tại</span>
            </div>
        </c:if>

        <div class="mb-3">
            <label class="form-label">Tải ảnh mới từ máy</label>
            <input class="form-control" type="file" name="avatarFile" accept="image/*">
            <div class="form-text">Hỗ trợ jpg, png, webp… (tối đa vài MB tuỳ cấu hình).</div>
        </div>

        <div class="mb-3">
            <label class="form-label">Hoặc dùng đường dẫn ảnh (URL)</label>
            <input class="form-control" name="avatarUrl" value="${u.avatarUrl}">
            <div class="form-text">Ví dụ: /uploads/me.png hoặc https://…</div>
        </div>

        <button class="btn btn-primary">Lưu</button>
        <a class="btn btn-secondary" href="${ctx}/profile">Huỷ</a>
    </form>
</section>
