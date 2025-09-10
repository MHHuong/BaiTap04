package vn.host.fillter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.host.constant.Constant;
import vn.host.entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/manager/*", "/category/*", "/user/*"})

public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getRequestURI().substring(req.getContextPath().length());
        int role = acc.getRoleid();

        if (path.startsWith("/admin/") && role != Constant.ROLE_ADMIN) {
            deny(resp, req, role);
            return;
        }

        if (path.startsWith("/manager/") && role != Constant.ROLE_MANAGER) {
            deny(resp, req, role);
            return;
        }
        if (path.startsWith("/user/") && role != Constant.ROLE_USER) {
            deny(resp, req, role);
            return;
        }

        chain.doFilter(request, response);
    }

    private void deny(HttpServletResponse resp, HttpServletRequest req, int role) throws IOException {
        switch (role) {
            case Constant.ROLE_ADMIN -> resp.sendRedirect(req.getContextPath() + Constant.ADMIN_HOME);
            case Constant.ROLE_MANAGER -> resp.sendRedirect(req.getContextPath() + Constant.MANAGER_HOME);
            case Constant.ROLE_USER -> resp.sendRedirect(req.getContextPath() + Constant.USER_HOME);
            default -> resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
