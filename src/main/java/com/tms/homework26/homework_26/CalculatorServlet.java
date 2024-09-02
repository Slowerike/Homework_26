package com.tms.homework26.homework_26;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Calculator", value = "/calc")

public class CalculatorServlet extends HttpServlet {
    private String num1;
    private String num2;
    private String typeOfOperation;
    private final HistoryRepository historyRepository = HistoryRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String attributes = req.getQueryString().toLowerCase();
        getParameters(attributes);
        String result = getResult(num1, num2, typeOfOperation);
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String answer = String.format("%s%s%s=%s", num1, typeOfOperation, num2, result);
        historyRepository.addHistory(answer);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");  // Установка мета-тега для HTML страницы
            out.println("<title>Calculator</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + answer + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }

    }

    private void getParameters(String attributes) {
        String[] values = attributes.split("&");
        for (String s : values) {
            String[] innerValues = s.split("=");
            for (String value : innerValues) {
                if (value.equalsIgnoreCase("num1")) {
                    num1 = innerValues[1];
                } else if (value.equalsIgnoreCase("num2")) {
                    num2 = innerValues[1];
                } else if (value.equalsIgnoreCase("type")) {
                    typeOfOperation = innerValues[1];
                }
            }
        }
    }

    private String getResult(String num1, String num2, String typeOfOperation) {
        double number1 = Double.parseDouble(num1);
        double number2 = Double.parseDouble(num2);
        String result;
        switch (typeOfOperation) {
            case "+" -> result = String.valueOf(number1 + number2);
            case "-" -> result = String.valueOf(number1 - number2);
            case "*" -> result = String.valueOf(number1 * number2);
            case "/" -> {
                if (number2 != 0) {
                    result = String.valueOf(number1 / number2);
                } else {
                    result = "Error";
                }
            }
            default -> result = "Invalid operation";
        }

        // Убираем дробные части, если результат целый
        if (result.matches("-?\\d+\\.0")) {
            result = result.replace(".0", "");
        }
        return result;
    }
}
