package vn.host.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.host.constant.Constant;
import vn.host.dao.CategoryDao;
import vn.host.dao.impl.CategoryDaoImpl;
import vn.host.entity.User;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/home")
public class AdminHomeController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User acc = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("role", acc.getRoleid());
        req.setAttribute("me", acc.getUserid());
        req.setAttribute("cateList", categoryDao.findAll());

        req.getRequestDispatcher("/WEB-INF/views/admin-home.jsp").forward(req, resp);
    }
}
