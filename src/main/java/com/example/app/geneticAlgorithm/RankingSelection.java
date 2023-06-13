package com.example.app.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RankingSelection {

    List<Chromosome> population;
    private final Random random;

    public RankingSelection(List<Chromosome> population) {
        this.population = population;
        this.random = new Random();
    }

    public List<Chromosome> selectParents(int numParents) {
        List<Chromosome> parents = new ArrayList<>();

        population.sort(Collections.reverseOrder());
        calculateRanks();

        Double totalRankSum = calculateTotalRankSum();
        int populationSize = population.size();

        for (int i = 0; i < numParents; i++) {
            double randomValue = random.nextDouble() * totalRankSum;
            Double current = 0.0;
            boolean parentSelected = false;

            for (Chromosome chromosome : population) {
                current += chromosome.getRank();

                if (current >= randomValue) {
                    parents.add(chromosome);
                    parentSelected = true;
                    break;
                }
            }
            if (!parentSelected) {
                parents.add(population.get(populationSize - 1));
            }
        }
        return parents;
    }

    private Double calculateTotalRankSum() {
        double total = 0.0;
        int populationSize = population.size();

        for (int i = 1; i <= populationSize; i++) {
            total += getRankValue(i, populationSize);
        }

        return total;
    }

    private Double getRankValue(Integer rank, Integer populationSize) {
        return (2.0 * rank) / (populationSize * (populationSize + 1));
    }

    private void calculateRanks() {
        int populationSize = population.size();

        for (Chromosome chromosome : population) {
            Double rank = (double) (populationSize - 1) / populationSize;
            chromosome.setRank(rank);
        }
    }
}
