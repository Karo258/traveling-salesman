package com.example.app.geneticAlgorithm.mutation;

import com.example.app.geneticAlgorithm.Chromosome;
import com.example.app.geneticAlgorithm.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PositionChangeMutation {

    private static final Double MUTATION_RATE = 0.1;

    public List<Chromosome> performMutation(List<Chromosome> population) {
        List<Chromosome> mutatedPopulation = new ArrayList<>();
        Random random = new Random();

        for (Chromosome chromosome : population) {

            if (random.nextDouble() < MUTATION_RATE) {
                Chromosome chromosome1 = mutateChromosome(chromosome);
                mutatedPopulation.add(chromosome1);
            } else {
                mutatedPopulation.add(chromosome);
            }
        }

        return mutatedPopulation;
    }

    private Chromosome mutateChromosome(Chromosome chromosome) {
        List<Point> pointList = new ArrayList<>(chromosome.getPointList());
        int size = chromosome.getPointList().size();
        Random random = new Random();
        int index1 = random.nextInt(size);
        int index2 = random.nextInt(size);

        while (index1 == index2) {
            index2 = random.nextInt(size);
        }

        Collections.swap(pointList, index1, index2);

        return new Chromosome(pointList);
    }
}