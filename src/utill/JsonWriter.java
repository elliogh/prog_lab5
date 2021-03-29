package utill;

import collection.Product;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Класс JsonWriter. Записывает коллекцию в файл Json
 */
public class JsonWriter {
    /**
     * Метод, записывающий коллекцию в файл
     * @param path путь к файлу
     * @param collection коллекция
     */
    public void writeToJson(String path,TreeMap<Integer, Product> collection) {
        Gson gson = new Gson();
        StringBuilder text = new StringBuilder();

        ArrayList<String> list = new ArrayList<>();

        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            list.add(gson.toJson(e.getValue()));
        }

        for (int i = 0; i < list.get(0).length() - 1; i++) {
            text.append(list.get(0).charAt(i));
        }
        text.append(",\n");
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.get(i).length() - 1; j++) {
                text.append(list.get(i).charAt(j));
            }
            text.append(",\n");
        }
        for (int i = 1; i < list.get(list.size() - 1).length(); i++) {
            text.append(list.get(list.size() - 1).charAt(i));
        }

        String str = String.valueOf(text);
        byte [] buffer = str.getBytes();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(buffer, 0, buffer.length);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка с IO");
        }
    }
}
