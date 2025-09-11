package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.host.constant.Constant;
import vn.host.entity.User;
import vn.host.services.AuthService;
import vn.host.services.impl.AuthServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User acc = mockAuth(username, password);

        if (acc == null) {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute(Constant.SESSION_ACCOUNT, acc);

        // Điều hướng theo role
        switch (acc.getRoleid()) {
            case Constant.ROLE_ADMIN   -> resp.sendRedirect(req.getContextPath() + "/admin/home");
            case Constant.ROLE_MANAGER -> resp.sendRedirect(req.getContextPath() + "/manager/home");
            default                    -> resp.sendRedirect(req.getContextPath() + "/user/home");
        }
    }

    private User mockAuth(String u, String p) {
        if ("admin".equals(u) && "123".equals(p)) {
            return User.builder().userid(1).username(u).fullname("Quản trị")
                    .roleid(Constant.ROLE_ADMIN).avatarUrl("/assets/img/avatars/admin.png").build();
        }
        if ("manager".equals(u) && "123".equals(p)) {
            return User.builder().userid(2).username(u).fullname("Quản lý")
                    .roleid(Constant.ROLE_MANAGER).avatarUrl("/assets/img/avatars/manager.png").build();
        }
        if ("user".equals(u) && "123".equals(p)) {
            return User.builder().userid(3).username(u).fullname("Thành viên")
                    .roleid(Constant.ROLE_USER).avatarUrl("/assets/img/avatars/user.png").build();
        }
        return null;
    }
}