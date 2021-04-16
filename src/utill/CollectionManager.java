package utill;

import collection.Person;
import collection.Product;
import main.App;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс Менджер колеекции(CollectionManager). Отвечает за управление коллекцией
 */
public class CollectionManager {
    private TreeMap<Integer, Product> collection;
    private LocalDate initDate;
    private JsonReader jsonReader = new JsonReader();
    private JsonParser jsonParser = new JsonParser();
    private JsonWriter jsonWriter = new JsonWriter();
    private ProductMaker productMaker = new ProductMaker();
    private boolean passportCheck;


    /**
     * Конструктор
     * Инициализация коллекции
     * Получение даты инициализации коллекции
     */
    public CollectionManager() {
        collection = new TreeMap<Integer, Product>();
        initDate = LocalDate.now();
    }

    /**
     * метод, который переводит строку в коллекцию
     * @param path путь к файлу
     */
    public void lineToCollection(String path) {
        try {
            ArrayList<String> lines = jsonReader.readAllLines(new File(path));
            if (lines.size() == 1) {
                lines.clear();
                System.out.println("Файл пустой");

            }
            for (int i = 0; i < lines.size(); i++) {
                collection.put(jsonParser.parseId(lines.get(i)), productMaker.makeProduct(lines.get(i)));
            }
        }catch (Exception e) {}
    }

    /**
     * Метод, возвращающий тип коллекции
     * @return тип колекции
     */
    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isUnitOfMeasure(String s) {
        return (s.equals("METERS") || s.equals("CENTIMETERS") || s.equals("GRAMS"));
    }

    public static boolean isDateFormat(String s) {
        return s.matches("\\d{2}[.]\\d{2}[.]\\d{4}");
    }

    /**
     * Метод, возвращающий дату инициализации коллекции
     * @return initDate
     */
    public LocalDate getInitDate() {
        return initDate;
    }

    /**
     * Метод, возвращающий количество элементов коллеции
     * @return размер коллекции
     */
    public int getNumberOfElements() {
        return collection.size();
    }

    public ArrayList<String> getPassportIDList() {
        ArrayList<String> list  = new ArrayList<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            list.add(e.getValue().getOwner().getPassportID());
        }
        return list;
    }

    public TreeMap<Integer, Product> getCollection() {
        return collection;
    }

    /**
     * Метод, реализующий команду show
     */
    public void show() {
        try {
            if (collection.isEmpty()) System.out.println("Коллекция пуста");
            else {
                for (Map.Entry<Integer, Product> e : collection.entrySet()) {
                    System.out.println(e.getValue());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Файл пустой");
        }
    }

    /**
     * Метод, реализующий команду insert
     * @param id ключ элемента
     */
    public void insert(int id) {
        passportCheck = true;
        collection.put(id, productMaker.insertProduct(passportCheck,getPassportIDList(), null));
        collection.get(id).setId(id);
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду update
     * @param id ключ эдемента
     */
    public void update(int id) {
        passportCheck = true;
        if (!collection.get(id).equals(null)) {
            collection.replace(id, productMaker.insertProduct(passportCheck, getPassportIDList(), null));
            System.out.println("Команда успешно выполнена");
        }
    }

    /**
     * Метод, реализующий команду remove_key
     * @param id ключ элемента
     */
    public void removeKey(int id) {
        if (!collection.get(id).equals(null)) {
            collection.remove(id);
            System.out.println("Команда успешно выполнена");
        }
    }

    /**
     * Метод, реализующий команду clean
     */
    public void clear() {
        collection.clear();
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду save
     */
    public void save() {
        jsonWriter.writeToJson(App.path, collection);
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду execute_script
     * @param listOfCommands список команд в скрипт-файле
     */
    public void executeScript(ArrayList<String> listOfCommands, ArrayList<String> listOfPaths) {
        if (listOfCommands.size() != 0) {
            boolean isExecution = true;
            try {
                for (int i = 0; i < listOfCommands.size(); i++) {
                    String [] command = listOfCommands.get(i).trim().split(" ");

                    if (command[0].equals("execute_script"))
                        if (listOfPaths.contains(command[1])) {
                            throw new Exception("Рекурсия");
                        }
                }
                for (int i = 0; i < listOfCommands.size(); i++) {
                    String[] input = listOfCommands.get(i).trim().split(" ");
                    if (input[0].equals("insert") || input[0].equals("update") || input[0].equals("remove_lower")) {

                        ArrayList<String> fields = new ArrayList<>();
                        for (int j = i+1; j < i+13; j++) {
                            fields.add(listOfCommands.get(j));
                        }

                        if (i+12 < listOfCommands.size()) i += 12;
                        else {
                            System.out.println("Недостаточно полей");
                            isExecution = false;
                            break;
                        }
                        if (isInteger(fields.get(1)) && isInteger(fields.get(2)) && isInteger((fields.get(3))) && isFloat((fields.get(5))) && isUnitOfMeasure((fields.get(6))) && isDateFormat(fields.get(8)) && isFloat(fields.get(9)) && isLong(fields.get(10))) {
                            collection.put(Integer.parseInt(input[1]), productMaker.insertProduct(true, getPassportIDList(), listOfPaths.get(listOfPaths.size() - 1)));
                            collection.get(Integer.parseInt(input[1])).setId(Integer.parseInt(input[1]));
                        }
                        else {
                            isExecution = false;
                            System.out.println("Что-то с полями. Проверьте поля");
                        }
                    }
                    else Parser.parseThenRun(input, App.getCommandManager());
                }
            } catch (Exception e) {
                isExecution = false;
                System.out.println("Ошибка файла скрипта. Проверьте свой файл на возможность рекурсии \n");
            }

            if (isExecution) System.out.println("Список команд из файла выполнен");
            else System.out.println("Список команд из файла выполнен не полностью");
        }
    }

    /**
     * Метод, реализующий команду remove_lower
     */
    public void removeLower() {
        passportCheck = false;
        Product product = productMaker.insertProduct(passportCheck, getPassportIDList(), null);
        HashSet<Integer> keys = new HashSet<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (e.getValue().hashCode() - product.hashCode() < 0) keys.add(e.getKey());
        }
        for (Integer s : keys) {
            collection.remove(s);
        }
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду remove_if_greater
     * @param id ключ элемента
     */
    public void replaceIfGreater(int id) {
        passportCheck = true;
        Product product = productMaker.insertProduct(passportCheck, getPassportIDList(), null);
        if (collection.get(id).hashCode() - product.hashCode() < 0) {
            collection.replace(id, product);
        }
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду remove_lower_key
     * @param id ключ элемента
     */
    public void removeLowerKey(int id) {
        HashSet<Integer> keys = new HashSet<>();
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (e.getKey() < id) keys.add(e.getKey());
        }

        for (Integer s : keys) {
            collection.remove(s);
        }
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду remove_all_by_owner
     */
    public void removeAllByOwner() {
        passportCheck = false;
        HashSet<Integer> keys = new HashSet();
        Person owner = productMaker.insertOwner(passportCheck,getPassportIDList(), new Scanner(System.in));

        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            if (owner.equals(e.getValue().getOwner())) {
                keys.add(e.getKey());
            }
        }

        for (Integer s : keys) {
            collection.remove(s);
        }
        System.out.println("Команда успешно выполнена");
    }

    /**
     * Метод, реализующий команду print_ascending
     */
    public void printAscending() {
        if (!collection.isEmpty()) {
            SortedSet<Product> sortedSet = new TreeSet<>(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getPrice() - o2.getPrice();
                }
            });
            for (Map.Entry<Integer, Product> e : collection.entrySet()) {
                sortedSet.add(e.getValue());
            }
            for (Product s : sortedSet) {
                System.out.println(s);
            }
        }
        else System.out.println("Коллекция пуста");
    }

    /**
     * Метод, реализующий команду print_descending
     */
    public void printDescending() {
        if (!collection.isEmpty()) {
            SortedSet<Product> sortedSet = new TreeSet<>(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getPrice() - o1.getPrice();
                }
            });
            for (Map.Entry<Integer, Product> e : collection.entrySet()) {
                sortedSet.add(e.getValue());
            }
            for (Product s : sortedSet) {
                System.out.println(s);
            }
        }
        else System.out.println("Коллекция пуста");
    }
}

