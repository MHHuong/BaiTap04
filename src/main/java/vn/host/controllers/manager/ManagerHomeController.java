package vn.host.controllers.manager;

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

@WebServlet(urlPatterns = "/manager/home")
public class ManagerHomeController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute(Constant.SESSION_ACCOUNT);
        req.setAttribute("cateList", categoryDao.findByUserId(u.getUserid()));
        req.getRequestDispatcher("WEB-INF/views/manager-home.jsp").forward(req, resp);
    }
}
