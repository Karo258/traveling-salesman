package com.example.app.geneticAlgorithm.selection;

import com.example.app.geneticAlgorithm.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteSelection {

    private final List<Chromosome> population;
    private final Random random;

    public RouletteSelection(List<Chromosome> population) {
        this.population = population;
        this.random = new Random();
    }

    public List<Chromosome> selectParents(int numParents) {
        List<Chromosome> parents = new ArrayList<>();
        Double totalFitness = calculateTotalFitness();

        for (int i = 0; i < numParents; i++) {
            double randomValue = random.nextDouble() * totalFitness;
            Double current = 0.0;

            for (Chromosome chromosome : population) {
                current += chromosome.getFitness();

                if (current >= randomValue) {
                    parents.add(chromosome);

                    break;
                }
            }
        }

        return parents;
    }

    private Double calculateTotalFitness() {
        Double fitness = 0.0;

        for (Chromosome chromosome : population) {
            fitness += chromosome.getFitness();
        }

        return fitness;
    }
}