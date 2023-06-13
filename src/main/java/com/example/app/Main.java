package com.example.app;

import com.example.app.fileHandling.ReadFromFile;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Traveling Salesman v1");
        boolean wasRead = false;
        Scanner scanner = new Scanner(System.in);

        while (!wasRead) {
            System.out.print("Type in the filename: ");
            String filename = scanner.nextLine();
            wasRead = ReadFromFile.read(filename);
        }

        scanner.close();
    }
}
