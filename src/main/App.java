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
    public static String path = "";


    /**
     * Конструктор
     */
    public App() {
        consoleScanner = new Scanner(System.in);
        collectionManager = new CollectionManager();
        commandManager = new CommandManager(consoleScanner, collectionManager);
    }

    public static void main(String[] args) {
        path = "./src/input_file.json";
        if (System.getenv("tselikov") != null) {
            path = System.getenv("tselikov");
        }
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
            try {
                System.out.print("> ");
                String input = consoleScanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }
                Parser.parseThenRun(input.split(" "), commandManager);
            } catch (NoSuchElementException e) {
                System.out.println("не душите пожалувста");
                System.exit(1);
            }
        }
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
