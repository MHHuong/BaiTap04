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

@WebServlet(urlPatterns = {"/category/create"})
public class CategoryCreateController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("mode", "create");
        req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User acc = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        int role = acc.getRoleid();

        // Manager không được tạo
        if (role == Constant.ROLE_MANAGER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String name = trim(req.getParameter("categoryname"));
        String images = trim(req.getParameter("images"));
        int status = parseIntSafe(req.getParameter("status"), 1);

        if (name == null || name.isEmpty()) {
            req.setAttribute("error", "Tên category không được để trống");
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
            return;
        }

        Category c = new Category();
        c.setCategoryname(name);
        c.setImages(images);
        c.setStatus(status);
        c.setOwner(acc);

        boolean ok = categoryDao.insert(c);
        if (!ok) {
            req.setAttribute("error", "Tạo category thất bại");
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/category/list");
    }

    private String trim(String s) {
        return s == null ? null : s.trim();
    }

    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}
