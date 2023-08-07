package com.example.app.geneticAlgorithm.mutation;

import com.example.app.geneticAlgorithm.Chromosome;
import com.example.app.geneticAlgorithm.Point;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class DijkstraAlgorithmMutation {

    private static final Double MUTATION_RATE = 0.1;

    public List<Chromosome> performMutation(List<Chromosome> population) {
        List<Chromosome> mutatedPopulation = new ArrayList<>();

        for (Chromosome chromosome: population) {
            Chromosome mutatedChromosome = mutate(chromosome);
            mutatedPopulation.add(mutatedChromosome);
        }

        return mutatedPopulation;
    }

    private Chromosome mutate(Chromosome chromosome) {
        if (Math.random() < MUTATION_RATE) {
            List<Point> currentPoints = chromosome.getPointList();
            List<Point> mutatedPoints = performDijkstra(currentPoints);

            List<Point> remainingPoints = new ArrayList<>(currentPoints);
            remainingPoints.removeAll(mutatedPoints);

            List<Point> combinedPointLists = new ArrayList<>(remainingPoints);
            combinedPointLists.addAll(mutatedPoints);

            return new Chromosome(combinedPointLists);
        } else {
            return chromosome;
        }
    }

    private List<Point> performDijkstra(List<Point> pointList) {
        int numberOfPoints = pointList.size();

        double[][] graph = new double[numberOfPoints][numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            Point source = pointList.get(i);
            for (int j = 0; j< numberOfPoints; j++) {
                Point target = pointList.get(j);
                graph[i][j] = calculateDistance(source, target);
            }
        }

        Random random = new Random();
        int index1 = random.nextInt(numberOfPoints);
        int index2;
        do {
            index2 = random.nextInt(numberOfPoints);
        } while (index1 == index2);

        List<Integer> shortestPath = dijkstra(graph, index1, index2);

        List<Point> mutatedPointList = new ArrayList<>(numberOfPoints);
        for (int index: shortestPath) {
            mutatedPointList.add(pointList.get(index));
        }

        return mutatedPointList;
    }


    private Double calculateDistance(Point point1, Point point2) {
        double xDistance = point1.getX() - point2.getX();
        double yDistance = point1.getY() - point2.getY();

        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    private List<Integer> dijkstra(double[][] graph, int start, int end) {
        int numberOfVertices = graph.length;
        double[] distances = new double[numberOfVertices];
        int[] previous = new int[numberOfVertices];
        boolean[] visited = new boolean[numberOfVertices];

        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        distances[start] = 0;

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(i -> distances[i]));
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            int current = priorityQueue.poll();
            if (current == end) {
                break;
            }
            if (visited[current]) {
                continue;
            }
            visited[current] = true;

            for (int neighbor = 0; neighbor < numberOfVertices; neighbor++){
                if (!visited[neighbor] && graph[current][neighbor] > 0) {
                    double newDistance = distances[current] + graph[current][neighbor];
                    if (newDistance < distances[neighbor]) {
                        distances[neighbor] = newDistance;
                        previous[neighbor] = current;
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }

        List<Integer> shortestPath = new ArrayList<>();
        int current = end;
        while (current != start) {
            shortestPath.add(current);
            current = previous[current];
        }
        shortestPath.add(start);
        Collections.reverse(shortestPath);

        return shortestPath;
    }
}
