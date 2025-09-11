package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.host.constant.Constant;
import vn.host.entity.User;
import vn.host.services.AuthService;
import vn.host.services.impl.AuthServiceImpl;
import vn.host.dao.impl.UserDaoImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final AuthService authService = new AuthServiceImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();

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

        // 1) Xác thực từ DB
        User authed = authService.login(username, password);
        if (authed == null) {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // 2) NẠP LẠI BẰNG ID để chắc chắn lấy dữ liệu mới nhất (fullname/phone/avatarUrl đã update)
        User fresh = userDao.findUserById(authed.getUserid());

        // 3) Set session đúng key
        HttpSession session = req.getSession(true);
        // không giữ password trong session cho an toàn
        if (fresh != null) fresh.setPassword(null);
        session.setAttribute(Constant.SESSION_ACCOUNT, fresh != null ? fresh : authed);

        // 4) Điều hướng theo role
        int role = (fresh != null ? fresh.getRoleid() : authed.getRoleid());
        if (role == Constant.ROLE_ADMIN) {
            resp.sendRedirect(req.getContextPath() + Constant.ADMIN_HOME);
        } else if (role == Constant.ROLE_MANAGER) {
            resp.sendRedirect(req.getContextPath() + Constant.MANAGER_HOME);
        } else {
            resp.sendRedirect(req.getContextPath() + Constant.USER_HOME);
        }
    }
}
