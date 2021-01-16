package com.example.projectpiggy2;

public class PriceFormatter {
    public static String format(double price) {
        double a = price * 100;
        int b = (int) a;
        double c = (double) b / 100;
        if (b % 10 == 0) return "$" + c + "0";
        else return "$" + c;
    }

    public static double unformat(String price) {
        return Double.parseDouble(price.substring(1));
    }
}