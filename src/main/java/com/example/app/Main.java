package com.example.app;

import com.example.app.fileHandling.ReadFromFile;
import com.example.app.geneticAlgorithm.Chromosome;
import com.example.app.geneticAlgorithm.FittnesEvaluator;
import com.example.app.geneticAlgorithm.Initializer;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String filename = "";
        System.out.println("Traveling Salesman v1");
        boolean wasRead = false;
        Scanner scanner = new Scanner(System.in);

        while (!wasRead) {
            System.out.print("Type in the filename: ");
            filename = scanner.nextLine();
            if (!ReadFromFile.read(filename).isEmpty()) {
                wasRead = true;
            }
        }

        scanner.close();

        Initializer initializer = new Initializer(filename, 50);
        List<Chromosome> population = initializer.initializePopulation();

        FittnesEvaluator fittnesEvaluator = new FittnesEvaluator(population);
        fittnesEvaluator.evaluate();

        for (Chromosome chromosome: population) {
            System.out.println(chromosome.getFitness());
        }
    }
}
