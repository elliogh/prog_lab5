package utill;

import com.sun.istack.internal.NotNull;
import commands.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс Парсер(Parser). Делает парсинг команд
 */
public class Parser {

    public static ArrayList<String> listOfPaths = new ArrayList<>();
    /**
     * Метод для парсинга и запуска команды
     * @param input аргументы команды
     * @param commandManager менеджер команд
     */
    public static void parseThenRun(String[] input, CommandManager commandManager) {
        String commandKey = input[0];
        String ar []  = Arrays.copyOfRange(input, 1, input.length);
        commandManager.runCommand(commandKey, ar);
    }

    /**
     * Метод, читающий команды из скрипт-файла
     * @param path путь к файлу
     * @return
     */
    public static ArrayList<String> readScript(String path) {
        File file = new File(path);
        ArrayList<String> listOfCommands = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                listOfCommands.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        return listOfCommands;
    }

    public static ArrayList<String> returnScriptPaths(String path) {
        listOfPaths.add(path);
        return listOfPaths;
    }
}
