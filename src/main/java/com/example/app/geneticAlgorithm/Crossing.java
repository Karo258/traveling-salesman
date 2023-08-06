package com.example.app.geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Point> childPointList = new ArrayList<>();
        Set<Integer> addedCityIds = new HashSet<>();

        for (int i = 0; i < size; i++) {
            Point point = p1.getPoint(i);
            if (addedCityIds.add(point.getCityId())) {
                childPointList.add(point);
            } else {
                Point replacementPoint = findReplacement(p2, point);
                childPointList.add(replacementPoint);
            }
        }

        return new Chromosome(childPointList);
    }

    private Point findReplacement(Chromosome parent, Point duplicatePoint) {
        for (Point point : parent.getPointList()) {
            if (!duplicatePoint.equals(point)) {
                return point;
            }
        }
        throw new IllegalArgumentException("Could not find a replacement point.");
    }
}