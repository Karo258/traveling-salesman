package com.example.app;

import com.example.app.fileHandling.ReadFromFile;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static Boolean wasRead;

    public static void main(String[] args) {

        System.out.println("Traveling Salesman v1");
        wasRead = false;
        scanner = new Scanner(System.in);

        while (!wasRead) {
            System.out.print("Type in the filename: ");
            String filename = scanner.nextLine();
            ReadFromFile reader = new ReadFromFile(filename);
            wasRead = ReadFromFile.read(filename);
        }

        scanner.close();
    }
}
