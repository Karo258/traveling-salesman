package com.example.app;

import com.example.app.fileHandling.ReadFromFile;
import com.example.app.geneticAlgorithm.*;

import java.util.ArrayList;
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

        Initializer initializer = new Initializer(filename, 50);
        List<Chromosome> population = initializer.initializePopulation();

        FitnessEvaluator fitnessEvaluator = new FitnessEvaluator(population);
        fitnessEvaluator.evaluate();

        boolean selectionChosen = false;
        List<Chromosome> parents = new ArrayList<>();
        while (!selectionChosen) {
            System.out.println("Select selection option. Type 1 for roulette selection method, 2 for ranking " +
                    "selection method and 3 for tournament selection method.");
            int selectionType = scanner.nextInt();
            if (selectionType == 1) {
                RouletteSelection selection = new RouletteSelection(population);
                parents = selection.selectParents(40);
                selectionChosen = true;
            } else if (selectionType == 2) {
                RankingSelection selection = new RankingSelection(population);
                parents = selection.selectParents(40);
                selectionChosen = true;
            } else if (selectionType == 3) {
                TournamentSelection selection = new TournamentSelection(population);
                parents = selection.selectParents(40);
                selectionChosen = true;
            } else {
                System.out.println("Please select one of the provided selection methods");
            }
        }

        scanner.close();

        for (Chromosome chromosome : parents) {
            System.out.println(chromosome.getPointList());
        }
    }
}
