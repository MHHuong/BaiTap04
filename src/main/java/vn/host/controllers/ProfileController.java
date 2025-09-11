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
        maxFileSize = 5 * 1024 * 1024,         // 5MB
        maxRequestSize = 10 * 1024 * 1024      // 10MB
)
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);

        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if ("/profile/edit".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/views/profile/profile-edit.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/profile/profile.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Quan trọng: với multipart, cần @MultipartConfig để getParameter hoạt động
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User acc = (session == null) ? null : (User) session.getAttribute(Constant.SESSION_ACCOUNT);
        if (acc == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Đọc các trường dạng text
        String fullname = trimOrNull(req.getParameter("fullname"));
        String phone    = trimOrNull(req.getParameter("phone"));
        String avatarUrlParam = trimOrNull(req.getParameter("avatarUrl")); // người dùng có thể nhập URL thủ công

        // Upload file (nếu có)
        String finalAvatarUrl = avatarUrlParam; // mặc định dùng URL đã nhập (nếu có)
        Part filePart = null;
        try {
            filePart = req.getPart("avatarFile");
        } catch (Exception ignored) {}

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
            String uploadPath = req.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);

            // Chọn 1 cách và dùng nhất quán:
            // C1) Lưu đường dẫn tuyệt đối theo context (hiển thị dùng ${u.avatarUrl}):
            finalAvatarUrl = req.getContextPath() + "/uploads/" + fileName;
            // C2) Hoặc lưu đường dẫn gốc app ("/uploads/..."), khi hiển thị phải dùng ${ctx}${u.avatarUrl}
            // finalAvatarUrl = "/uploads/" + fileName;
        }

        // Cập nhật DB
        UserDaoImpl dao = new UserDaoImpl();
        boolean ok = dao.updateProfile(acc.getUserid(),
                (fullname != null ? fullname : acc.getFullname()),
                (phone != null ? phone : acc.getPhone()),
                (finalAvatarUrl != null ? finalAvatarUrl : acc.getAvatarUrl())
        );

        if (!ok) {
            req.setAttribute("error", "Cập nhật thất bại!");
            req.getRequestDispatcher("/WEB-INF/views/profile/profile-edit.jsp").forward(req, resp);
            return;
        }

        // Nạp lại user mới lên session để topbar/profile hiển thị ngay giá trị mới
        User refreshed = dao.findUserById(acc.getUserid());
        session.setAttribute(Constant.SESSION_ACCOUNT, refreshed);

        resp.sendRedirect(req.getContextPath() + "/profile");
    }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }
}
