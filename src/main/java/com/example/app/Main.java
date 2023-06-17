package com.example.app;

import com.example.app.fileHandling.ReadFromFile;
import com.example.app.geneticAlgorithm.*;
import com.example.app.geneticAlgorithm.selection.RankingSelection;
import com.example.app.geneticAlgorithm.selection.RouletteSelection;
import com.example.app.geneticAlgorithm.selection.TournamentSelection;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Integer POPULATION_SIZE = 30;
    private static final Integer NUM_PARENTS = 50;

    public static void main(String[] args) {

        String filename = "";
        System.out.println("Traveling Salesman");
        boolean wasRead = false;
        Scanner scanner = new Scanner(System.in);

        while (!wasRead) {
            System.out.print("Type in the filename: ");
            filename = scanner.nextLine();
            if (!ReadFromFile.read(filename).isEmpty()) {
                wasRead = true;
            }
        }

        Initializer initializer = new Initializer(filename, POPULATION_SIZE);
        List<Chromosome> population = initializer.initializePopulation();

        FitnessEvaluator fitnessEvaluator = new FitnessEvaluator(population);
        fitnessEvaluator.evaluate();

        boolean selectionChosen = false;
        int selectionType = 0;
        while (!selectionChosen) {
            System.out.println("Select selection option. Type 1 for roulette selection method, 2 for ranking " +
                    "selection method and 3 for tournament selection method.");
            selectionType = scanner.nextInt();
            if (selectionType != 1 && selectionType != 2 && selectionType != 3) {
                System.out.println("Please select one of the provided selection methods");
            } else {
                selectionChosen = true;
            }
        }

        for (int i = 0; i < 200; i++) {
            List<Chromosome> parents;
            if (selectionType == 1) {
                RouletteSelection selection = new RouletteSelection(population);
                parents = selection.selectParents(NUM_PARENTS);
            } else if (selectionType == 2) {
                RankingSelection selection = new RankingSelection(population);
                parents = selection.selectParents(NUM_PARENTS);
            } else {
                TournamentSelection selection = new TournamentSelection(population);
                parents = selection.selectParents(NUM_PARENTS);
            }

            Crossing crossing = new Crossing();
            List<Chromosome> children = crossing.performCrossing(parents);

            Mutation mutation = new Mutation();

            population = mutation.performMutation(children);
            fitnessEvaluator = new FitnessEvaluator(population);
            fitnessEvaluator.evaluate();
        }
        scanner.close();

        Double minimumPath = Double.MAX_VALUE;
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() < minimumPath) {
                minimumPath = chromosome.getFitness();
            }
        }
        System.out.println(minimumPath);
    }
}
