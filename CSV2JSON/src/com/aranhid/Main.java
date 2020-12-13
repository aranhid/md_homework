package com.aranhid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        FileInputStream csv;
        List<App> apps = new ArrayList<>();

        try {
            csv = new FileInputStream("googleplaystore.csv");
        }
        catch (FileNotFoundException e){
            System.out.println(e);
            return;
        }
        Scanner scanner = new Scanner(csv);
        String columnsString = scanner.nextLine();
        String[] columns = columnsString.split(",");
        int index = 0;
        while (scanner.hasNext())
        {
            String string = scanner.nextLine();
            String[] values = string.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            App app = new App(columns, values);
            apps.add(app);
        }

        Map<String, List<App>> sortedApps = new HashMap<>();

        for (App app:apps) {
            String category;
            if (app.category == null) {
                category = "NOT_DETECTED";
            }
            else {
                category = app.category;
            }

            if (!sortedApps.containsKey(category)){
                sortedApps.put(category, new ArrayList<>());
            }

            List<App> temp = sortedApps.get(category);
            temp.add(app);
            sortedApps.put(category, temp);
            System.out.println(apps.indexOf(app) + " " + app.app);
        }

        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().setPrettyPrinting().create();
        try {
            Writer fileWriter = new FileWriter("sorted.json");
            gson.toJson(sortedApps, fileWriter);
            fileWriter.close();
            System.out.println("OK");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
