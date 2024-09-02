package com.tms.homework26.homework_26;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "History", value = "/history")
public class HistoryServlet extends HttpServlet {
    private final HistoryRepository historyRepository = HistoryRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String history = historyRepository.getHistory();
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");  // Установка мета-тега для HTML страницы
            out.println("<title>History</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + "История" + "</h1>");
            out.println(history);
            out.println("</body>");
            out.println("</html>");
        }
    }
}
