package vn.host.fillter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.host.constant.Constant;
import vn.host.entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*", "/manager/*", "/admin/*", "/category/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int role = acc.getRoleid();

        if (path.startsWith("/admin/") && role != Constant.ROLE_ADMIN) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (path.startsWith("/user/") && role != Constant.ROLE_USER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (path.startsWith("/manager/") && role != Constant.ROLE_MANAGER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}
