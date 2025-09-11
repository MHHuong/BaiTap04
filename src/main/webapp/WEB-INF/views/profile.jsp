<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html>
<head>
    <title>Hồ sơ cá nhân</title>
</head>
<body>
<div class="row">
    <div class="col-md-8 col-lg-6 mx-auto">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title mb-3">Cập nhật hồ sơ</h5>

                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label class="form-label">Họ và tên</label>
                        <input name="fullname" type="text" class="form-control"
                               value="${user.fullname}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Điện thoại</label>
                        <input name="phone" type="text" class="form-control"
                               value="${user.phone}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ảnh đại diện</label><br/>
                        <c:if test="${not empty user.images}">
                            <img src="${pageContext.request.contextPath}/image?fname=${user.images}"
                                 alt="avatar" style="max-width:120px" class="img-thumbnail mb-2">
                        </c:if>
                        <input name="images" type="file" class="form-control" accept="image/*">
                        <div class="form-text">Chấp nhận ảnh ≤ 5MB.</div>
                    </div>
                    <div class="d-grid">
                        <button class="btn btn-primary" type="submit">Lưu thay đổi</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
