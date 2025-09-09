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
import java.util.List;

@WebServlet(urlPatterns = {"/category/list"})
public class CategoryListController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User acc = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        int role = acc.getRoleid();

        List<Category> cateList;
        if (role == Constant.ROLE_ADMIN || role == Constant.ROLE_MANAGER) {
            cateList = categoryDao.findAll();
        } else {
            cateList = categoryDao.findByUserId(acc.getUserid());
        }

        req.setAttribute("cateList", cateList);
        req.getRequestDispatcher("/WEB-INF/views/category/list.jsp").forward(req, resp);
    }
}
