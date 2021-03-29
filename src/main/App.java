package main;

import commands.CommandManager;
import utill.*;
import java.util.*;
/**
 * Основной класс программы
 */
public class App {
    Scanner consoleScanner;
    static CommandManager commandManager;
    CollectionManager collectionManager;
    public static final String path = "./src/input_file.json";

    /**
     * Конструктор
     */
    public App() {
        consoleScanner = new Scanner(System.in);
        collectionManager = new CollectionManager();
        commandManager = new CommandManager(consoleScanner, collectionManager);
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    /**
     * Метод, запускающий работу программы
     */
    public void start() {
        collectionManager.lineToCollection(path);
        System.out.println("Начало работы программы:");
        while (true) {
            String[] input = consoleScanner.nextLine().trim().split(" ");
            Parser.parseThenRun(input, commandManager);
        }
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
