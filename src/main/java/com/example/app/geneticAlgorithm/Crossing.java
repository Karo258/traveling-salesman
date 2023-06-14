package com.example.app.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

    public List<Chromosome> performCrossing(List<Chromosome> parents) {
        List<Chromosome> offspring = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Chromosome p1 = parents.get(i);
            Chromosome p2 = parents.get(i + 1);
            Chromosome c1 = createChild(p1, p2);
            Chromosome c2 = createChild(p2, p1);

            offspring.add(c1);
            offspring.add(c2);
        }
        return offspring;
    }

    private Chromosome createChild(Chromosome p1, Chromosome p2) {
        int size = p1.getPointList().size();
        int halfSize = size / 2;

        List<Point> childPointList = new ArrayList<>();

        for (int i = 0; i < halfSize; i++) {
            childPointList.add(p1.getPoint(i));
        }
        for (int i = halfSize; i < size; i++) {
            childPointList.add(p2.getPoint(i));
        }

        return new Chromosome(childPointList);
    }
}
