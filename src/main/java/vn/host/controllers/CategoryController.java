package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.host.constant.Constant;
import vn.host.dao.CategoryDao;
import vn.host.dao.impl.CategoryDaoImpl;
import vn.host.entity.Category;
import vn.host.entity.User;

import java.io.IOException;

public class CategoryController extends HttpServlet {
    private final CategoryDao dao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        String path = req.getPathInfo();
        if (path == null || "/".equals(path) || "/list".equals(path)) {
            req.setAttribute("cateList", dao.findByUserId(u.getUserid()));
            req.getRequestDispatcher("/WEB-INF/views/category/list.jsp").forward(req, resp);
            return;
        }
        if ("/new".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
            return;
        }
        if (path.startsWith("/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category owned = dao.findOwnedById(id, u.getUserid());
            if (owned == null) {
                resp.sendError(403);
                return;
            }
            req.setAttribute("item", owned);
            req.getRequestDispatcher("/WEB-INF/views/category/form.jsp").forward(req, resp);
            return;
        }
        if (path.startsWith("/view")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Category any = dao.findById(id);
            if (any == null) {
                resp.sendError(404);
                return;
            }
            req.setAttribute("item", any);
            req.getRequestDispatcher("/WEB-INF/views/category/view.jsp").forward(req, resp);
        }
        if (path.startsWith("/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            if (!dao.deleteOwned(id, u.getUserid())) {
                resp.sendError(403);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/category/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        String path = req.getPathInfo();

        if ("/create".equals(path)) {
            User me = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
            Category c = new Category();
            c.setCategoryname(req.getParameter("categoryname"));
            c.setImages(req.getParameter("images"));
            c.setStatus(Integer.parseInt(req.getParameter("status")));
            c.setOwner(me);
            dao.insert(c);
            return;
        }

        if ("/update".equals(path)) {
            int id = Integer.parseInt(req.getParameter("categoryid"));
            Category owned = dao.findOwnedById(id, u.getUserid());
            if (owned == null) {
                resp.sendError(403);
                return;
            }
            owned.setCategoryname(req.getParameter("categoryname"));
            owned.setImages(req.getParameter("images"));
            owned.setStatus(Integer.parseInt(req.getParameter("status")));
            dao.updateOwned(owned, u.getUserid());
            resp.sendRedirect(req.getContextPath() + "/category/list");
        }
    }
}
