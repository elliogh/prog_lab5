package commands;

import collection.Product;
import utill.CollectionManager;
import utill.Parser;
import java.util.*;

/**
 * Класс Менеджер команд(CommandManager). Отвечает за выполнение команд и их хранение
 */
public class CommandManager {
    public Receiver receiver;
    private Scanner consoleScanner;
    private static HashMap<String, Command> commandMap = new HashMap<>();
    private CollectionManager collectionManager;

    /**
     * Конструктор
     * @param scanner добавляет сканнер команд
     * @param collectionManager добавляет упрвление коллекцией
     */
    public CommandManager(Scanner scanner, CollectionManager collectionManager) {
        this.receiver = new Receiver();
        this.consoleScanner = scanner;
        this.collectionManager = collectionManager;
        addCommands(
                new HelpCommand("help"," - вывести справку по доступным командам"),
                new InfoCommand("info"," - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"),
                new ShowCommand("show"," - вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
                new InsertCommand("insert"," - добавить новый элемент с заданным ключом"),
                new UpdateCommand("update"," - обновить значение элемента коллекции, id которого равен заданному"),
                new RemoveKeyCommand("remove_key"," - удалить элемент из коллекции по его ключу"),
                new ClearCommand("clear"," - очистить коллекцию"),
                new SaveCommand("save"," - сохранить коллекцию в файл"),
                new ExecuteScriptCommand("execute_script"," - считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."),
                new ExitCommand("exit"," - завершить программу (без сохранения в файл)"),
                new RemoveLowerCommand("remove_lower"," - удалить из коллекции все элементы, меньшие, чем заданный"),
                new ReplaceIfGreaterCommand("replace_if_greater"," - заменить значение по ключу, если новое значение больше старого"),
                new RemoveLowerKeyCommand("remove_lower_key"," - удалить из коллекции все элементы, ключ которых меньше, чем заданный"),
                new RemoveAllByOwnerCommand("remove_all_by_owner"," - удалить из коллекции все элементы, значение поля owner которого эквивалентно заданному"),
                new PrintAscendingCommand("print_ascending"," - вывести элементы коллекции в порядке возрастания "),
                new PrintDescendingCommand("print_descending"," - вывести элементы коллекции в порядке убывания")
        );
    }

    /**
     * Метод, запускающий выполение команды
     * @param commandKey ключ команды
     * @param args аргументы команды
     */
    public void runCommand(String commandKey, String [] args){
        if (!commandMap.containsKey(commandKey)) {
            System.out.println("Такой команды не существует. Введите команду help для просмотра существующих команд");
        }
        else {
            commandMap.get(commandKey).execute(args);
        }
    }

    /**
     * Метод, добавляющицй все команды в хэщ-таблицу
     * @param commands команды
     */
    private void addCommands(Command ... commands) {
        for (Command command : commands) {
            commandMap.put(command.getKey(), command);
            command.setCommandManager(this);
        }
    }

    public Collection<Command> getAllCommands() {
        return commandMap.values();
    }

    public static ArrayList<String> getArrayOfCommands() {
        return new ArrayList<String>(commandMap.keySet());
    }

    /**
     * Класс Receiver(получатель). Здесь описана логика выполняемых команд
     */
    public class Receiver {

        public void help() {
            System.out.println("Доступные команды:");
            getAllCommands().stream().map(command -> command.getKey() + command.getHelpText()).forEach(System.out::println);
            System.out.println();
            System.out.println(getArrayOfCommands());
        }

        public void info() {
            for (Map.Entry<Integer, Product> e : collectionManager.getCollection().entrySet()) {
                if (e.getValue().equals(null)) {
                    System.out.println("Ошибка");
                    System.exit(1);
                }
            }

            System.out.println("Тип коллекции: " + collectionManager.getCollectionType());
            System.out.println("Дата инициализации: " + collectionManager.getInitDate());
            System.out.println("Количество элементов: " + collectionManager.getNumberOfElements());
        }

        public void show() {
            collectionManager.show();
        }

        public void insert(int id) {
            collectionManager.insert(id);
        }

        public void update(int id) {
            collectionManager.update(id);
        }

        public void removeKey(int id) {
            collectionManager.removeKey(id);
        }

        public void clear() {
            collectionManager.clear();
        }

        public void save() {
            collectionManager.save();
        }

        public void executeScript(String filename) {
            collectionManager.executeScript(Parser.readScript(filename), Parser.returnScriptPaths(filename));
            Parser.listOfPaths.clear();
        }

        public void exit() {
            System.out.println("Завершение работы программы ...");
            System.exit(1);
        }

        public void removeLower() {
            collectionManager.removeLower();
        }

        public void replaceIfGreater(int id) {
            collectionManager.replaceIfGreater(id);
        }

        public void removeLowerKey(int id) {
            collectionManager.removeLowerKey(id);
        }

        public void removeAllByOwner() {
            collectionManager.removeAllByOwner();
        }

        public void printAscending() {
            collectionManager.printAscending();
        }

        public void printDescending() {
            collectionManager.printDescending();
        }
    }
}
