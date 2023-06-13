package com.example.app.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TournamentSelection {

    List<Chromosome> population;
    Random random;

    public TournamentSelection(List<Chromosome> population) {
        this.population = population;
        this.random = new Random();
    }

    public List<Chromosome> selectParents(int numParents) {
        List<Chromosome> parents = new ArrayList<>();

        int populationSize = population.size();

        for (int i = 0; i < numParents; i++) {
            int tournamentSize = Math.min(5, populationSize);

            List<Chromosome> tournament = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(populationSize);
                tournament.add(population.get(randomIndex));
            }
            Chromosome bestParent = Collections.max(tournament);
            parents.add(bestParent);
        }
        return parents;
    }

}
