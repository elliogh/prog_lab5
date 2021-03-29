package utill;

import collection.Coordinates;
import collection.Person;
import collection.UnitOfMeasure;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс парсер Json(JsonParser)
 */
public class JsonParser {
    public int parseId(String line) {
        int id = 0;
        Pattern patternId = Pattern.compile("(?<=\"id\":)\\d+");
        Matcher matcherId = patternId.matcher(line);
        if (matcherId.find()) id = Integer.parseInt(line.substring(matcherId.start(), matcherId.end()));
        return id;
    }
    public String parseName(String line) {
        String name = "";
        Pattern patternName = Pattern.compile("(?<=\"name\":\")\\w+");
        Matcher matcherName = patternName.matcher(line);
        if (matcherName.find()) name = line.substring(matcherName.start(), matcherName.end());
        return name;
    }
    public Coordinates parseCoordinates(String line) {
        double x = 0;
        Float y = 0F;
        Pattern patternCoordinatesX = Pattern.compile("(?<=\"x\":)\\d+.\\d+");
        Matcher matcherCoordinatesX = patternCoordinatesX.matcher(line);
        Pattern patternCoordinatesY = Pattern.compile("(?<=\"y\":)\\d+.\\d+");
        Matcher matcherCoordinatesY = patternCoordinatesY.matcher(line);
        if (matcherCoordinatesX.find()) x = Double.parseDouble(line.substring(matcherCoordinatesX.start(), matcherCoordinatesX.end()));
        if (matcherCoordinatesY.find()) y = Float.parseFloat(line.substring(matcherCoordinatesY.start(), matcherCoordinatesY.end()));
        return new Coordinates(x,y);
    }
    public Date parseCreationDate(String line) {
        String stringMonth = "";
        Pattern patternMonth = Pattern.compile("(?<=\"creationDate\":\")\\w+");
        Matcher matcherMonth = patternMonth.matcher(line);
        if (matcherMonth.find()) stringMonth = (line.substring(matcherMonth.start(), matcherMonth.end()));

        int month = findMonth(stringMonth);

        int year = 0;
        Pattern patternYear = Pattern.compile("(?<=\\d{2}, )\\d+");
        Matcher matcherYear = patternYear.matcher(line);
        if (matcherYear.find()) year = Integer.parseInt(line.substring(matcherYear.start(), matcherYear.end())) - 1900;

        int date = 0;
        Pattern patternDate = Pattern.compile("(?<=\\w{3} )\\d{2}");
        Matcher matcherDate = patternDate.matcher(line);
        if (matcherDate.find()) date = Integer.parseInt(line.substring(matcherDate.start(), matcherDate.end()));

        return new Date(year,month, date);
    }
    public int parsePrice(String line) {
        int price = 0;
        Pattern patternPrice = Pattern.compile("(?<=\"price\":)\\d+");
        Matcher matcherPrice = patternPrice.matcher(line);
        if (matcherPrice.find()) price = Integer.parseInt(line.substring(matcherPrice.start(), matcherPrice.end()));
        return price;
    }
    public String parsePartNumber(String line) {
        String partNumber = "";
        Pattern patternPartNumber = Pattern.compile("(?<=\"partNumber\":\")\\w+");
        Matcher matcherPartNumber = patternPartNumber.matcher(line);
        if (matcherPartNumber.find()) partNumber =line.substring(matcherPartNumber.start(), matcherPartNumber.end());
        return partNumber;
    }
    public Float parseManufactureCost(String line) {
        Float manufactureCost = 0F;
        Pattern patternManufactureCost = Pattern.compile("(?<=\"manufactureCost\":)\\d+.\\d+");
        Matcher matcherManufactureCost = patternManufactureCost.matcher(line);
        if (matcherManufactureCost.find()) manufactureCost = Float.parseFloat(line.substring(matcherManufactureCost.start(), matcherManufactureCost.end()));
        return manufactureCost;
    }
    public UnitOfMeasure parseUnitOfMeasure(String line) {
        UnitOfMeasure unitOfMeasure = null;
        Pattern patternUnitOfMeasure = Pattern.compile("(?<=\"unitOfMeasure\":\")\\w+");
        Matcher matcherUnitOfMeasure = patternUnitOfMeasure.matcher(line);
        if (matcherUnitOfMeasure.find()) {
            switch (line.substring(matcherUnitOfMeasure.start(), matcherUnitOfMeasure.end())) {
                case ("METERS"):
                    unitOfMeasure = UnitOfMeasure.METERS;
                    break;
                case ("CENTIMETERS"):
                    unitOfMeasure = UnitOfMeasure.CENTIMETERS;
                    break;
                case ("GRAMS"):
                    unitOfMeasure = UnitOfMeasure.GRAMS;
                    break;
            }
        }
        return unitOfMeasure;
    }
    public Person parseOwner(String line) {
        String name = "";
        Pattern patternName = Pattern.compile("(?<=[{]\"name\":\")\\w+");
        Matcher matcherName = patternName.matcher(line);
        if (matcherName.find()) name = line.substring(matcherName.start(), matcherName.end());


        String stringMonth = "";
        Pattern patternMonth = Pattern.compile("(?<=\"birthday\":\")\\w+");
        Matcher matcherMonth = patternMonth.matcher(line);
        if (matcherMonth.find()) stringMonth = (line.substring(matcherMonth.start(), matcherMonth.end()));

        int month = findMonth(stringMonth);

        int year = 0;
        Pattern patternYear = Pattern.compile("(?<=\"birthday\":\"\\w{3} \\d{2}, )\\d+");
        Matcher matcherYear = patternYear.matcher(line);
        if (matcherYear.find()) year = Integer.parseInt(line.substring(matcherYear.start(), matcherYear.end())) - 1900;

        int date = 0;
        Pattern patternDate = Pattern.compile("(?<=\"birthday\":\"\\w{3} )\\d{2}");
        Matcher matcherDate = patternDate.matcher(line);
        if (matcherDate.find()) date = Integer.parseInt(line.substring(matcherDate.start(), matcherDate.end()));
        Date birthday = new Date(year,month,date);


        Float height = 0F;
        Pattern patternHeight = Pattern.compile("(?<=\"height\":)\\d+.\\d+");
        Matcher matcherHeight = patternHeight.matcher(line);
        if (matcherHeight.find()) height = Float.parseFloat(line.substring(matcherHeight.start(), matcherHeight.end()));

        long weight = 0;
        Pattern patternWeight = Pattern.compile("(?<=\"weight\":)\\d+");
        Matcher matcherWeight = patternWeight.matcher(line);
        if (matcherWeight.find()) weight = Long.parseLong(line.substring(matcherWeight.start(), matcherWeight.end()));

        String passportID = "";
        Pattern patternPassportID = Pattern.compile("(?<=\"passportID\":\")\\d*\\w*");
        Matcher matcherPassportID = patternPassportID.matcher(line);
        if (matcherPassportID.find()) passportID = line.substring(matcherPassportID.start(), matcherPassportID.end());

        return new Person(name, birthday, height, weight, passportID);
    }
    public int findMonth(String stringMonth) {
        int month = 0;
        switch (stringMonth) {
            case ("Jan") :
                month = 0;
                break;
            case ("Feb") :
                month = 1;
                break;
            case ("Mar") :
                month = 2;
                break;
            case ("Apr") :
                month = 3;
                break;
            case ("May") :
                month = 4;
                break;
            case ("Jun") :
                month = 5;
                break;
            case ("Jul") :
                month = 6;
                break;
            case ("Aug") :
                month = 7;
                break;
            case ("Sep") :
                month = 8;
                break;
            case ("Oct") :
                month = 9;
                break;
            case ("Nov") :
                month = 10;
                break;
            case ("Dec") :
                month = 11;
                break;
        }
        return month;
    }
}
