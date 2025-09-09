<%--
  Created by IntelliJ IDEA.
  User: MAI HONG TIN
  Date: 9/9/2025
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html><html><body>
<h2>Đăng nhập</h2>
<c:if test="${not empty alert}">
    <div style="color:red">${alert}</div>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/login">
    <input name="username" placeholder="Username" required>
    <input name="password" type="password" placeholder="Password" required>
    <button>Login</button>
</form>
</body></html>
