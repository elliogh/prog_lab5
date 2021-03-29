package utill;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Класс Читатель Json(JsonReader). Читает исходный файл
 */
public class JsonReader {
    /**
     * Читает все строки из исходного файла
     * @param file читаемый файл
     * @return arrayList строк
     */
    public ArrayList<String> readAllLines(File file) {
        checkAllPermissions(file);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            StringBuilder line = new StringBuilder();
            while (true) {
                int data = inputStreamReader.read();
                if (data != -1) {
                    line.append((char) data);
                }
                else {
                    break;
                }
            }
            String[] lines = String.valueOf(line).split(System.getProperty("line.separator"));
            return new ArrayList<>(Arrays.asList(lines));
        } catch (FileNotFoundException e) {
            System.out.println("Файл был не найден");
            return null;
        } catch (IOException e) {
            System.out.println("Что-то с IO");
            System.exit(1);
            return null;
        }
    }

    /**
     * Метод, проверяющий права у файла
     * @param file файл
     */
    public void checkAllPermissions(File file) {
        if (file.exists()) {
            if ((!file.canWrite()) && (!file.canRead())) {
                System.out.println("Файл нельзя считать и записать");
                System.exit(1);
            } else if ((!file.canWrite()) && file.canRead()) {
                System.out.println("Нельзя записать в файл");
                System.exit(1);
            } else if (file.canWrite() && (!file.canRead())) {
                System.out.println("Файл нельзя считать");
                System.exit(1);
            }
        }
    }
}
