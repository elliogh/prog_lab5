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
        for (Map.Entry<Integer, Product> e : collection.entrySet()) {
            System.out.println(e.getValue());
        }
    }

    /**
     * Метод, реализующий команду insert
     * @param id ключ элемента
     */
    public void insert(int id) {
        passportCheck = true;
        collection.put(id, productMaker.insertProduct(passportCheck,getPassportIDList()));
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
            collection.replace(id, productMaker.insertProduct(passportCheck, getPassportIDList()));
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
    public void executeScript(ArrayList<String> listOfCommands) {
        try {
            for (int i = 0; i < listOfCommands.size(); i++) {
                if (listOfCommands.get(i).trim().split(" ")[0].equals("execute_script"))
                    throw new Exception("Рекурсия");
            }
            for (int i = 0; i < listOfCommands.size(); i++) {
                String [] input = listOfCommands.get(i).trim().split(" ");
                Parser.parseThenRun(input, App.getCommandManager());
            }
            System.out.println("Список команд из файла успешно выполнен");

        } catch (Exception e) {
            System.out.println("Ошибка файла скрипта. Проверьте свой файл на возможность рекурсии");
        }
    }

    /**
     * Метод, реализующий команду remove_lower
     */
    public void removeLower() {
        passportCheck = false;
        Product product = productMaker.insertProduct(passportCheck, getPassportIDList());
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
        Product product = productMaker.insertProduct(passportCheck, getPassportIDList());
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
        Person owner = productMaker.insertOwner(passportCheck,getPassportIDList());

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
        SortedSet<Product> sortedSet = new TreeSet<>(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getPrice() - o2.getPrice();
            }
        });
        for(Map.Entry<Integer, Product> e : collection.entrySet()) {
            sortedSet.add(e.getValue());
        }
        for (Product s : sortedSet) {
            System.out.println(s);
        }
    }

    /**
     * Метод, реализующий команду print_descending
     */
    public void printDescending() {
        SortedSet<Product> sortedSet = new TreeSet<>(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getPrice() - o1.getPrice();
            }
        });
        for(Map.Entry<Integer, Product> e : collection.entrySet()) {
            sortedSet.add(e.getValue());
        }
        for (Product s : sortedSet) {
            System.out.println(s);
        }
    }
}

