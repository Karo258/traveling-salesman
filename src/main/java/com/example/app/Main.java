package com.example.app;

import com.example.app.fileHandling.ReadFromFile;
import com.example.app.geneticAlgorithm.*;
import com.example.app.geneticAlgorithm.mutation.DijkstraAlgorithmMutation;
import com.example.app.geneticAlgorithm.mutation.PositionChangeMutation;
import com.example.app.geneticAlgorithm.optimzation.TwoOptimal;
import com.example.app.geneticAlgorithm.selection.RankingSelection;
import com.example.app.geneticAlgorithm.selection.RouletteSelection;
import com.example.app.geneticAlgorithm.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Integer POPULATION_SIZE = 30;
    private static final Integer NUM_REPLACEMENTS = POPULATION_SIZE / 2;

    public static void main(String[] args) {
        String filename = "";
        boolean wasRead = false;
        boolean selectionChosen = false;
        int selectionType = 0;
        boolean replacementChosen = false;
        int replacementType = 0;
        Double minimumPath = Double.MAX_VALUE;
        Double previousMinimumPath;
        boolean finished = false;
        boolean mutationChosen = false;
        int mutationType = 0;
        Chromosome finalChromosome = null;
        boolean optimizationChosen = false;
        int optimizationType = 0;
        Long startTime = System.nanoTime();

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

        while (!replacementChosen) {
            System.out.println("Select replacement option. Type 1 for replacing the whole population and 2 for " +
                    "replacing only the worst elements of the old population.");
            replacementType = scanner.nextInt();

            if (replacementType != 1 && replacementType != 2) {
                System.out.println("Please select one of the provided replacement options.");
            } else {
                replacementChosen = true;
            }
        }

        while (!mutationChosen) {
            System.out.println("Select mutation option. Type 1 for changing a position of points in the chromosome " +
                    "and 2 for using the Dijkstra algorithm.");
            mutationType = scanner.nextInt();

            if (mutationType != 1 && mutationType != 2) {
                System.out.println("Please select one of the provided mutation options.");
            } else {
                mutationChosen = true;
            }
        }

        while (!optimizationChosen) {
            System.out.println("Select if you want optimization. Type 1 for optimization and 2 for no optimization.");
            optimizationType = scanner.nextInt();

            if (optimizationType != 1 && optimizationType != 2) {
                System.out.println("Please select one of the provided optimization options.");
            } else {
                optimizationChosen = true;
            }
        }

        while (!finished) {
            List<Chromosome> parents;

            if (selectionType == 1) {
                RouletteSelection selection = new RouletteSelection(population);
                parents = selection.selectParents(POPULATION_SIZE);
            } else if (selectionType == 2) {
                RankingSelection selection = new RankingSelection(population);
                parents = selection.selectParents(POPULATION_SIZE);
            } else {
                TournamentSelection selection = new TournamentSelection(population);
                parents = selection.selectParents(POPULATION_SIZE);
            }

            Crossing crossing = new Crossing();
            List<Chromosome> children = crossing.performCrossing(parents);
            List<Chromosome> mutated;

            if (mutationType == 1) {
                PositionChangeMutation positionChangeMutation = new PositionChangeMutation();
                mutated = positionChangeMutation.performMutation(children);
            } else {
                DijkstraAlgorithmMutation dijkstraAlgorithmMutation = new DijkstraAlgorithmMutation();
                mutated = dijkstraAlgorithmMutation.performMutation(children);
            }

            fitnessEvaluator = new FitnessEvaluator(mutated);
            fitnessEvaluator.evaluate();
            List<Chromosome> optimized = new ArrayList<>();

            if (optimizationType == 1) {

                for (Chromosome value : mutated) {
                    Chromosome optimalChromosome = TwoOptimal.performTwoOptimal(value);
                    optimized.add(optimalChromosome);
                }
            } else {
                optimized = mutated;
            }

            fitnessEvaluator = new FitnessEvaluator(optimized);
            fitnessEvaluator.evaluate();

            if (replacementType == 1) {
                Replacement replacement = new Replacement();
                population = replacement.replace(optimized);
            } else {
                Replacement replacement = new Replacement(NUM_REPLACEMENTS);
                population = replacement.replaceWorst(population, optimized);
            }

            fitnessEvaluator = new FitnessEvaluator(population);
            fitnessEvaluator.evaluate();
            previousMinimumPath = minimumPath;

            for (Chromosome chromosome : population) {

                if (chromosome.getFitness() < minimumPath) {
                    minimumPath = chromosome.getFitness();
                    finalChromosome = chromosome;
                }
            }

            if (Math.abs(previousMinimumPath - minimumPath) < 0.1) {
                finished = true;
            }
        }

        scanner.close();
        Long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println("Minimal path length is: " + minimumPath);
        System.out.print("The route: ");

        for (Point point : finalChromosome.getPointList()) {
            System.out.print(point.getCityId() + ", ");
        }

        System.out.println();
        System.out.println("Calculation time: " + time * Math.pow(10, -9) + "s");
    }
}