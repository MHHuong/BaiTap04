package vn.host.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/image")

public class ImageController extends HttpServlet {
    private File uploadDir;

    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("/uploads");
        uploadDir = new File(path);
        if (!uploadDir.exists()) uploadDir.mkdirs();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fname = req.getParameter("fname");
        if (fname == null || fname.isBlank()) { resp.sendError(404); return; }
        File f = new File(uploadDir, fname);
        if (!f.exists()) { resp.sendError(404); return; }

        String ct = getServletContext().getMimeType(f.getName());
        if (ct == null) ct = "application/octet-stream";
        resp.setContentType(ct);

        try (InputStream in = new FileInputStream(f);
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}
