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

@WebServlet(urlPatterns = {"/category/edit"})
public class CategoryEditController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User acc = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        int role = acc.getRoleid();

        int id = parseIntSafe(req.getParameter("id"), -1);
        if (id <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Category target = categoryDao.findById(id);
        if (target == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Manager chỉ xem form (nếu muốn) nhưng POST sẽ bị chặn.
        // User chỉ được sửa khi là chủ sở hữu => hiển thị form vẫn OK,
        // còn POST sẽ kiểm tra chặt hơn. Nếu muốn chặn ngay tại GET thì có thể:
        if (role == Constant.ROLE_USER) {
            // Nếu không sở hữu thì 403
            Category owned = categoryDao.findOwnedById(id, acc.getUserid());
            if (owned == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        req.setAttribute("category", target);
        req.setAttribute("mode", "edit");
        req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
    }

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

        // Manager không được sửa
        if (role == Constant.ROLE_MANAGER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Category target = categoryDao.findById(id);
        if (target == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // User chỉ được sửa nếu sở hữu
        if (role == Constant.ROLE_USER) {
            if (categoryDao.findOwnedById(id, acc.getUserid()) == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        String name = trim(req.getParameter("categoryname"));
        String images = trim(req.getParameter("images"));
        int status = parseIntSafe(req.getParameter("status"), target.getStatus());

        if (name == null || name.isEmpty()) {
            req.setAttribute("error", "Tên category không được để trống");
            req.setAttribute("category", target);
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
            return;
        }

        target.setCategoryname(name);
        target.setImages(images);
        target.setStatus(status);

        boolean ok = categoryDao.update(target);
        if (!ok) {
            req.setAttribute("error", "Cập nhật category thất bại");
            req.setAttribute("category", target);
            req.setAttribute("mode", "edit");
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