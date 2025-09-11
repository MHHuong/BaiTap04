package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.host.constant.Constant;
import vn.host.dao.impl.UserDaoImpl;
import vn.host.entity.User;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/profile", "/profile/edit"})
@MultipartConfig(
        fileSizeThreshold = 1 * 1024 * 1024,   // 1MB
        maxFileSize = 10 * 1024 * 1024,        // 10MB
        maxRequestSize = 20 * 1024 * 1024      // 20MB
)
public class ProfileController extends HttpServlet {

    private static String trimOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String uri = req.getRequestURI();
        if (uri.endsWith("/edit")) {
            req.getRequestDispatcher("/WEB-INF/views/profile/profile-edit.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullname = trimOrNull(req.getParameter("fullname"));
        String phone    = trimOrNull(req.getParameter("phone"));
        String avatarUrlParam = trimOrNull(req.getParameter("avatarUrl"));

        // Ưu tiên file upload; nếu không có file thì dùng URL nhập tay (avatarUrlParam)
        String finalAvatarUrl = null;

        Part filePart = null;
        try {
            filePart = req.getPart("avatarFile");
        } catch (IllegalStateException ignore) {}

        if (filePart != null && filePart.getSize() > 0) {
            // Lưu vào /uploads bên trong webapp (thư mục thực tế trong container)
            String uploadsDir = req.getServletContext().getRealPath("/uploads");
            File dir = new File(uploadsDir);
            if (!dir.exists()) dir.mkdirs();

            // tên file an toàn (có thể thêm random/UUID nếu bạn muốn)
            String submitted = filePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + submitted.replaceAll("[^a-zA-Z0-9._-]", "_");
            File saved = new File(dir, fileName);
            filePart.write(saved.getAbsolutePath());

            // Lưu đường dẫn tương đối để render trên web
            finalAvatarUrl = req.getContextPath() + "/uploads/" + fileName;
        } else if (avatarUrlParam != null) {
            finalAvatarUrl = avatarUrlParam; // có thể là /uploads/xxx hoặc URL tuyệt đối http(s)
        }

        UserDaoImpl dao = new UserDaoImpl();
        boolean ok = dao.updateProfile(
                acc.getUserid(),
                (fullname != null ? fullname : acc.getFullname()),
                (phone != null ? phone : acc.getPhone()),
                (finalAvatarUrl != null ? finalAvatarUrl : acc.getAvatarUrl())
        );

        if (!ok) {
            req.setAttribute("error", "Cập nhật thất bại!");
            req.getRequestDispatcher("/WEB-INF/views/profile/profile-edit.jsp").forward(req, resp);
            return;
        }

        // Reload user -> cập nhật session ngay để topbar/profile hiển thị mới
        User refreshed = dao.findUserById(acc.getUserid());
        session.setAttribute(Constant.SESSION_ACCOUNT, refreshed);

        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
