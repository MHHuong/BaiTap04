<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="u" value="${sessionScope.ACCOUNT}"/>

<section class="card p-4" style="max-width:720px;margin:32px auto;">
    <div class="d-flex align-items-center">
        <img src="${empty u.avatarUrl ? (ctx.concat('/assets/img/avatars/default.png')) : u.avatarUrl}"
             alt="avatar"
             style="width:96px;height:96px;border-radius:50%;object-fit:cover;"/>
        <div>
            <h4 class="mb-1">${u.fullname}</h4>
            <div class="text-muted">Username: <code>${u.username}</code></div>
            <div class="text-muted">UserID: ${u.userid}</div>
            <div class="text-muted">Role: ${u.roleid}</div>
            <div class="text-muted">SĐT: ${u.phone}</div>
            <img src="${ctx}/uploads/${u.avatarUrl}" alt="avatar"
                 style="width:96px;height:96px;border-radius:50%;object-fit:cover;"/>
        </div>
    </div>
    <hr/>
    <a class="btn btn-primary" href="${ctx}/profile/edit">Chỉnh sửa</a>
</section>