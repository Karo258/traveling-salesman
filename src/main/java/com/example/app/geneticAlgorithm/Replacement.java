package com.example.app.geneticAlgorithm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Replacement {

    Integer numReplacements;

    public List<Chromosome> replace(List<Chromosome> population) {
        return population;
    }

    public List<Chromosome> replaceWorst(List<Chromosome> oldPopulation, List<Chromosome> newPopulation) {
        Collections.sort(oldPopulation);
        newPopulation.sort(Collections.reverseOrder());
        int index = oldPopulation.size() - numReplacements;

        for (int i = 0; i < numReplacements; i++) {
            oldPopulation.set(index + i, newPopulation.get(i));
        }

        return oldPopulation;
    }
}