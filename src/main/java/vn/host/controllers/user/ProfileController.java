package vn.host.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.host.constant.Constant;
import vn.host.dao.UserDao;
import vn.host.dao.impl.UserDaoImpl;
import vn.host.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/user/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,        // 1MB
        maxFileSize = 5L * 1024 * 1024,         // 5MB
        maxRequestSize = 10L * 1024 * 1024      // 10MB
)
public class ProfileController extends HttpServlet {

    private UserDao userDao;
    private File uploadDir;

    @Override
    public void init() throws ServletException {
        this.userDao = new UserDaoImpl();
        String path = getServletContext().getRealPath("/uploads");
        uploadDir = new File(path);
        if (!uploadDir.exists()) uploadDir.mkdirs();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ss = req.getSession(false);
        User acc = (ss == null) ? null : (User) ss.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        User u = userDao.findUserById(acc.getUserid());
        req.setAttribute("user", u);
        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession ss = req.getSession(false);
        User acc = (ss == null) ? null : (User) ss.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        String fullname = req.getParameter("fullname");
        String phone    = req.getParameter("phone");

        Part imagePart = req.getPart("images");
        String imageFileName = null;

        if (imagePart != null && imagePart.getSize() > 0) {
            String ct = imagePart.getContentType();
            if (ct == null || !(ct.startsWith("image/"))) {
                req.setAttribute("error", "File ảnh không hợp lệ!");
                doGet(req, resp);
                return;
            }
            String submitted = imagePart.getSubmittedFileName();
            String ext = "";
            int dot = (submitted == null) ? -1 : submitted.lastIndexOf('.');
            if (dot >= 0) ext = submitted.substring(dot);

            imageFileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
            File dst = new File(uploadDir, imageFileName);
            imagePart.write(dst.getAbsolutePath());
            dst.setReadable(true, false);
        }

        boolean ok = userDao.updateProfile(acc.getUserid(), fullname, phone, imageFileName);
        if (ok) {
            User fresh = userDao.findUserById(acc.getUserid());
            ss.setAttribute(Constant.SESSION_ACCOUNT, fresh);
            req.setAttribute("success", "Cập nhật thông tin thành công!");
        } else {
            req.setAttribute("error", "Có lỗi khi cập nhật!");
        }
        doGet(req, resp);
    }
}
