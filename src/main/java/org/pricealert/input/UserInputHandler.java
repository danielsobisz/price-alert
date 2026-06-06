package org.pricealert.input;

import java.util.Scanner;

public class UserInputHandler {
    public static String getUrl() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Choose a website to scrap:");
        return reader.next();
    }
}
