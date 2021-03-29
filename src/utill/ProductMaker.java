package utill;

import collection.*;

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
    public Product insertProduct(boolean passportCheck ,ArrayList<String> listOfPassportIDs) {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        Product product = new Product(0, null, null, null, 0,  null, null, null, null);

        while (true) {
            System.out.println("Введите значение поля name ");
            if (scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
            }
            if (line.equals(null) || line.equals("")) {
                System.out.println("Ошибка ввода");
            }
            else {
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
                coordinates.setX(Double.valueOf(line));
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
                if (Float.valueOf(line) <= 834) {
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
                if (Integer.valueOf(line) > 0) {
                    product.setPrice(Integer.valueOf(line));
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

        Person owner = insertOwner(passportCheck,listOfPassportIDs);
        product.setOwner(owner);

        return product;
    }

    /**
     * Метод, создающий экземпляр класса Person из командной строки
     * @param passportCheck
     * @param listOfPassportIDs
     * @return owner
     */
    public Person insertOwner(boolean passportCheck,ArrayList<String> listOfPassportIDs) {
        Scanner scanner = new Scanner(System.in);
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
                System.out.println("Введите значение даты ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Integer.parseInt(line) > 0 && Integer.parseInt(line) < 32) {
                    date = Integer.parseInt(line);
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение месяца ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Integer.parseInt(line) > 0) {
                    month = (Integer.parseInt(line) - 1) % 12;
                    break;
                } else throw new Exception("Wrong input");
            } catch (Exception e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите значение года ");
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                }
                if (Integer.parseInt(line) >= 0) {
                    year = Integer.parseInt(line) - 1900;
                    break;
                } else throw new Exception("Wrong input");
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
                if (Float.valueOf(line) > 0) {
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
                if (Long.valueOf(line) > 0) {
                    owner.setWeight(Long.valueOf(line));
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
