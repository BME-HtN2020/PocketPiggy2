package com.example.projectpiggy2;

public class DataWrapper {
    public static String wrap(String[] dataList) {
        String dataEntry = "";
        for (int i = 0; i < dataList.length; i++) {
            dataEntry += dataList[i];
        }
        return dataEntry;
    }

    public static String[] unwrap(String dataEntry) {
        return dataEntry.split(";");
    }

    public static String pack(String originalEntry, String newData) {
        return originalEntry + newData;
    }
}