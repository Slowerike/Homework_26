package com.tms.homework26.homework_26;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryRepository {
    private final List<String> history;
    private static HistoryRepository instance;

    private HistoryRepository() {
        history = Collections.synchronizedList(new ArrayList<>());
    }

    public static synchronized HistoryRepository getInstance() {
        if (instance == null) {
            instance = new HistoryRepository();
        }
        return instance;
    }

    public  String getHistory() {
        StringBuilder sb = new StringBuilder();
        for (String line : history) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public void addHistory(String lineOfHistory) {
        // Получение текущего времени и даты
        LocalDateTime now = LocalDateTime.now();

        // Определение формата для отображения
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Форматирование текущего времени
        String formattedNow = now.format(formatter);

        history.add("<p>" + formattedNow + "-->" + lineOfHistory + "</p>");
    }
}
