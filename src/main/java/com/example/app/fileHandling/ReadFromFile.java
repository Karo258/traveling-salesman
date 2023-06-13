package com.example.app.fileHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadFromFile {

    private String filename;

    public static Boolean read(String filename) {
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            return true;
        } catch (IOException e) {
            System.out.println("The file doesn't exist.");
            return false;
        }
    }

}
