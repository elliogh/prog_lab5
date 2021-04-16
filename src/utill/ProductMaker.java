package utill;

import collection.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс создатель продуктов(ProductMaker)
 */
public class ProductMaker {
    JsonParser jsonParser = new JsonParser();

    /**
     * Метод, возращающий продукт из строки
     * @param line строка
     * @return product
     */
    public Product makeProduct(String line) {
        return new Product(
                jsonParser.parseId(line),
                jsonParser.parseName(line),
                jsonParser.parseCoordinates(line),
                jsonParser.parseCreationDate(line),
                jsonParser.parsePrice(line),
                jsonParser.parsePartNumber(line),
                jsonParser.parseManufactureCost(line),
                jsonParser.parseUnitOfMeasure(line),
                jsonParser.parseOwner(line)
        );
    }

    /**
     * Метод, создающий экземпляр класса Product из командной строки
     * @param passportCheck
     * @param listOfPassportIDs
     * @return product
     */
    public Product insertProduct(boolean passportCheck ,ArrayList<String> listOfPassportIDs, String path) {
        Scanner scanner = new Scanner(System.in);
        if (path != null) {
            try {
                scanner = new Scanner(new File(path));
                while (true) {
                    if (scanner.hasNextLine()) {
                        if (scanner.nextLine().trim().split(" ")[0].equals("insert")) {
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
        }
        String line = "";
        Product product = new Product(0, null, null, null, 0,  null, null, null, null);

        while (true) {
            System.out.println("Введите значение поля name ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
            if (line.equals(null) || line.equals("")) {
                System.out.println("Ошибка ввода");
            } else {
                product.setName(line);
                break;
            }
        }

        Coordinates coordinates = new Coordinates(0, null);
        System.out.println("Введем координаты");

        while (true) {
            try {
                System.out.println("Введите значение поля для координаты X ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                coordinates.setX(Double.parseDouble(line));
                break;
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение поля для координаты Y ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Float.parseFloat(line) <= 834) {
                    coordinates.setY(Float.valueOf(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }
        product.setCoordinates(coordinates);

        product.setCreationDate();

        while (true) {
            try {
                System.out.println("Введите значение для поля price ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Integer.parseInt(line) > 0) {
                    product.setPrice(Integer.parseInt(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля partNumber ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals(null) || line.equals("")) {
                    throw new Exception("Wrong input");
                } else {
                    product.setPartNumber(line);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля manufactureCost ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals(null) && line.equals("")) {
                    throw new Exception("Wrong input");
                }
                product.setManufactureCost(Float.valueOf(line));
                break;
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля unitOfMeasure ");
                System.out.println("Возможные варианты : " + UnitOfMeasure.METERS + ", " + UnitOfMeasure.CENTIMETERS + ", " + UnitOfMeasure.GRAMS);
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals("METERS") || line.equals("CENTIMETERS") || line.equals("GRAMS")) {
                    switch (line) {
                        case ("METERS"):
                            product.setUnitOfMeasure(UnitOfMeasure.METERS);
                            break;
                        case ("CENTIMETERS") :
                            product.setUnitOfMeasure(UnitOfMeasure.CENTIMETERS);
                            break;
                        case ("GRAMS") :
                            product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
                            break;
                    }
                    break;
                }
                else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        Person owner = insertOwner(passportCheck, listOfPassportIDs, scanner);
        product.setOwner(owner);

        return product;
    }



    /**
     * Метод, создающий экземпляр класса Person из командной строки
     * @param passportCheck
     * @param listOfPassportIDs
     * @return owner
     */
    public Person insertOwner(boolean passportCheck,ArrayList<String> listOfPassportIDs, Scanner scanner) {
        String line = "";
        Person owner = new Person(null, null, null, 0, null);
        System.out.println("Введем владельца");

        while (true) {
            System.out.println("Введите значение для поля name ");
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
            }
            if (line.equals(null) || line.equals("")) {
                System.out.println("Ошибка ввода");
            }
            else {
                owner.setName(line);
                break;
            }
        }

        int date = 0;
        int month = 0;
        int year = 0;

        while (true) {
            try {
                System.out.println("Введите дату в формате DD.MM.YYYY");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.length() == 10 && line.matches("\\d{2}[.]\\d{2}[.]\\d{4}") ) {
                    String [] str = line.split("\\.");

                    if (Integer.parseInt(str[0]) > 0 && Integer.parseInt(str[0]) < 32) {
                        date = Integer.parseInt(str[0]);
                    }

                    if (Integer.parseInt(str[1]) > 0 && Integer.parseInt(str[1]) < 13) {
                        month = Integer.parseInt(str[1]) - 1;
                    }

                    if (Integer.parseInt(str[2]) > 0) {
                        year = Integer.parseInt(str[2]) - 1900;
                    }
                    break;
                }
                else System.out.println("Дата введена неверно");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        Date birthday = new Date(year, month, date);
        owner.setBirthday(birthday);

        while (true) {
            try {
                System.out.println("Введите значение для поля height ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (line.equals(null) || line.equals("") || line.equals("null")) {
                    owner.setHeight(null);
                    break;
                }
                if (Float.parseFloat(line) > 0) {
                    owner.setHeight(Float.valueOf(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение для поля weight ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Long.parseLong(line) > 0) {
                    owner.setWeight(Long.parseLong(line));
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        if (passportCheck) {
            while (true) {
                try {
                    System.out.println("Введите значение для поля passportID ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if ((line.length() <= 50 && line.length() >= 6) && (!listOfPassportIDs.contains(line))) {
                        owner.setPassportID(line);
                        break;
                    } else throw new Exception("Wrong input");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }
        }
        else {
            while (true) {
                try {
                    System.out.println("Введите значение для поля passportID ");
                    if (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                    }
                    if (line.length() <= 50 && line.length() >= 6) {
                        owner.setPassportID(line);
                        break;
                    } else throw new Exception("Wrong input");
                } catch (Exception e) {
                    System.out.println("Ошибка ввода");
                }
            }
        }
        return owner;
    }

}
