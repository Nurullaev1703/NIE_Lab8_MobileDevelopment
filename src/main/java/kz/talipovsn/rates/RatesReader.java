package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "https://www.iqair.com/ru/kazakhstan/pavlodar"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("Уровень загрязнения воздуха:\n"); // Считываем заголовок страницы
            Elements e = doc.select("div.table-wrapper"); // Ищем в документе "<div class="exchange"> с данными о валютах
            Elements tables = e.select("table"); // Ищем таблицы с котировками
            Element table = tables.get(1); // Берем 1 таблицу
            int i = 0;
            // Цикл по строкам таблицы
            for (Element row : table.select("tr")) {
                // Цикл по столбцам таблицы
                for (Element col : row.select("td:lt(2)")) { //
                    data.append(String.format("%12s ", col.text()));
                    data.append("\n");// Считываем данные с ячейки таблицы
                }
                data.append("\n"); // Добавляем переход на следующую строку;
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}