package vn.host.fillter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.host.constant.Constant;
import vn.host.entity.User;

import java.io.IOException;
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PREFIXES = {
            "/login", "/logout", "/assets/", "/css/", "/js/", "/images/"
    };

    private boolean isPublic(String path) {
        for (String p : PUBLIC_PREFIXES) {
            if (path.startsWith(p)) return true;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ctx  = req.getContextPath();
        String path = req.getRequestURI().substring(ctx.length());

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);

        if (acc == null) {
            resp.sendRedirect(ctx + "/login");
            return;
        }

        // Phân quyền sâu hơn (ví dụ chặn vào /admin nếu không phải admin)
        if (path.startsWith("/admin/")   && acc.getRoleid() != Constant.ROLE_ADMIN)   { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
        if (path.startsWith("/manager/") && acc.getRoleid() == Constant.ROLE_USER)    { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }

        chain.doFilter(request, response);
    }
}