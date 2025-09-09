package vn.host.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.host.dao.CategoryDao;
import vn.host.dao.impl.CategoryDaoImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/user/home"})
public class UserHomeController extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cateList", categoryDao.findAll());
        req.getRequestDispatcher("/WEB-INF/views/user-home.jsp").forward(req, resp);
    }
}
