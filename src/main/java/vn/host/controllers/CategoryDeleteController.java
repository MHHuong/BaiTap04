package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.host.constant.Constant;
import vn.host.dao.CategoryDao;
import vn.host.dao.impl.CategoryDaoImpl;
import vn.host.entity.Category;
import vn.host.entity.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/category/delete"})
public class CategoryDeleteController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    // Chỉ chấp nhận POST để xóa (an toàn hơn)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User acc = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        int role = acc.getRoleid();

        int id = parseIntSafe(req.getParameter("id"), -1);
        if (id <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Manager không được xóa
        if (role == Constant.ROLE_MANAGER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Category target = categoryDao.findById(id);
        if (target == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // User chỉ được xóa nếu sở hữu
        if (role == Constant.ROLE_USER) {
            if (categoryDao.findOwnedById(id, acc.getUserid()) == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        boolean ok = categoryDao.delete(id);
        if (!ok) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Xóa thất bại");
            return;
        }

        switch (role) {
            case Constant.ROLE_ADMIN -> resp.sendRedirect(req.getContextPath() + Constant.ADMIN_HOME);
            case Constant.ROLE_MANAGER -> resp.sendRedirect(req.getContextPath() + Constant.MANAGER_HOME);
            case Constant.ROLE_USER -> resp.sendRedirect(req.getContextPath() + Constant.USER_HOME);
            default -> resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}