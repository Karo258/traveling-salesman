package com.example.app.geneticAlgorithm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class FittnesEvaluator {

    private List<Chromosome> population;

    public void evaluate() {
        for (Chromosome chromosome: population) {
            Double fitness = calculateFitness(chromosome);
            chromosome.setFitness(fitness);
        }
    }

    private Double calculateFitness(Chromosome chromosome) {
        List<Point> pointList = chromosome.getPointList();
        Double totalDistance = 0.0;

        for (int i = 0; i < pointList.size() - 1; i++) {
            Point current = pointList.get(i);
            Point next = pointList.get(i+1);
            Double distance = calculateDistance(current, next);
            totalDistance += distance;
        }

        Point first = pointList.get(0);
        Point last = pointList.get(pointList.size() - 1);
        Double distance = calculateDistance(last, first);
        totalDistance += distance;

        return totalDistance;
    }

    private Double calculateDistance(Point point1, Point point2) {
        Double xDifference = point1.getX() - point2.getX();
        Double yDifference = point1.getY() - point2.getY();

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }
}
