package com.aranhid;

import java.util.*;

public class App {
    String app;
    String category;
    Float rating;
    Integer reviews;
    String size;
    Integer installs;
    String type;
    Boolean price;
    String contentRating;
    List<String> genres;
    String lastUpdate;
    String currentVer;
    String androidVer;
    String csvString;

//    App(String[] values) {
//        app = values[0];
//        category = values[1];
//        reviews = values[2];
//        size = values[3];
//        installs = values[4];
//        type = values[5];
//        price = values[6];
//        contentRating = values[7];
//        genres = values[8];
//        lastUpdate = values[9];
//        currentVer = values[10];
//        androidVer = values[11];
//    }

    App(String[] columns, String[] values) {
        if (columns.length == values.length)
        {
            for (int i = 0; i < columns.length; i++){
                parseColumn(columns[i], values[i]);
            }
        }
        else {
            csvString = String.join(",", values);
        }
    }

    private void parseColumn(String column, String value) {
        switch (column) {
            case "App":
                app = value;
                break;
            case "Category":
                category = value;
                break;
            case "Rating":
                rating = Float.parseFloat(value);
                break;
            case "Reviews":
                reviews = Integer.parseInt(value);
                break;
            case "Size":
                size = value;
                break;
            case "Installs":
                installs = parseInstalls(value);
            case "Type":
                type = value;
                break;
            case "Price":
                price = parsePrice(value);
                break;
            case "Content Rating":
                contentRating = value;
                break;
            case "Genres":
                genres = parseGenres(value);
                break;
            case "Last Update":
                lastUpdate = value;
                break;
            case "Current Ver":
                currentVer = value;
                break;
            case "Android Ver":
                androidVer = parseAndroidVer(value);
                break;
        }
    }

    private Integer parseInstalls(String value){
        value = value.replaceAll("[\"+,]", "");
        return Integer.parseInt(value);
    }

    private Boolean parsePrice(String value){
        Boolean ret = true;
        if (value == "0") {
            ret = false;
        }
        return ret;
    }

    private List<String> parseGenres(String value){
        List<String> ret = new ArrayList<String>(Arrays.asList(value.split(";")));
        return ret;
    }

    private String parseAndroidVer(String value){
        String ret = "API ";
        Map<String, Integer> api = new HashMap<>();
        api.put("1.0", 1);
        api.put("1.1", 2);
        api.put("1.5", 3);
        api.put("1.6", 4);
        api.put("2.0", 5);
        api.put("2.0.1", 6);
        api.put("2.1", 7);
        api.put("2.2", 8);
        api.put("2.3", 9);
        api.put("2.3.3", 10);
        api.put("3.0", 11);
        api.put("3.1", 12);
        api.put("3.2", 13);
        api.put("4.0", 14);
        api.put("4.0.3", 15);
        api.put("4.1", 16);
        api.put("4.2", 17);
        api.put("4.3", 18);
        api.put("4.4", 19);
        api.put("4.4W", 20);
        api.put("5.0", 21);
        api.put("5.1", 22);
        api.put("6.0", 23);
        api.put("7.0", 24);
        api.put("7.1", 25);
        api.put("8.0", 26);
        api.put("8.1", 27);
        api.put("9", 28);
        api.put("10", 29);
        api.put("11", 30);
        value = value.replaceAll(" and up", "");
        if (value.length() < 6 && !value.contains("NaN"))
        {
            return ret + api.get(value);
        }
        return value;
    }
}
