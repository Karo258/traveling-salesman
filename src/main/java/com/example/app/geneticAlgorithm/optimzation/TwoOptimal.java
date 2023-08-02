package com.example.app.geneticAlgorithm.optimzation;

import com.example.app.geneticAlgorithm.Chromosome;
import com.example.app.geneticAlgorithm.Point;

import java.util.ArrayList;
import java.util.List;

public class TwoOptimal {

    public static Chromosome performTwoOptimal(Chromosome chromosome) {
        List<Point> current = chromosome.getPointList();
        int size = current.size();
        boolean improvement = true;

        while (improvement) {
            improvement = false;

            for (int i = 0; i < size - 1; i++) {
                for (int j = i + 1; j < size; j++) {
                    List<Point> tmp = twoOptimalSwap(current, i, j);

                    Double currentFitness = calculateFitness(current);
                    Double tmpFitness = calculateFitness(tmp);

                    if (tmpFitness < currentFitness) {
                        current = tmp;
                        improvement = true;
                    }
                }
            }
        }

        return new Chromosome(current);
    }

    private static Double calculateDistance(Point point1, Point point2) {
        double xDistance = point1.getX() - point2.getX();
        double yDistance = point1.getY() - point2.getY();

        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    private static Double calculateFitness(List<Point> pointList) {
        double totalDistance = 0.0;
        for (int i = 0; i < pointList.size() - 1; i++) {
            Point current = pointList.get(i);
            Point next = pointList.get(i + 1);
            totalDistance += calculateDistance(current, next);
        }

        totalDistance += calculateDistance(pointList.get(pointList.size() - 1), pointList.get(0));

        return totalDistance;
    }

    private static List<Point> twoOptimalSwap(List<Point> tour, int i, int j) {
        List<Point> newTour = new ArrayList<>();

        for (int c = 0; c <= i - 1; c++) {
            newTour.add(tour.get(c));
        }

        for (int c = j; c >= i; c--) {
            newTour.add(tour.get(c));
        }

        for (int c = j + 1; c < tour.size(); c++) {
            newTour.add(tour.get(c));
        }

        return newTour;
    }
}