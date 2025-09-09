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

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private final AuthService authService = new AuthServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User u = authService.login(username, password);
        if (u == null) {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute(Constant.SESSION_ACCOUNT, u);
        switch (u.getRoleid()) {
            case Constant.ROLE_USER -> resp.sendRedirect(req.getContextPath() + Constant.USER_HOME);
            case Constant.ROLE_ADMIN -> resp.sendRedirect(req.getContextPath() + Constant.ADMIN_HOME);
            case Constant.ROLE_MANAGER -> resp.sendRedirect(req.getContextPath() + Constant.MANAGER_HOME);
            default -> resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
