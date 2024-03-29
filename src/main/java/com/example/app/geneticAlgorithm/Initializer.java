package com.example.app.geneticAlgorithm;

import com.example.app.fileHandling.ReadFromFile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Initializer {

    private String filename;
    private Integer populationSize;

    public List<Chromosome> initializePopulation() {
        List<Chromosome> population = new ArrayList<>();
        List<Point> pointList = ReadFromFile.read(filename);
        int pointListSize = pointList.size();

        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome = new Chromosome(pointListSize);
            List<Point> shuffledPointList = new ArrayList<>(pointList);
            Collections.shuffle(shuffledPointList);
            chromosome.setPointList(shuffledPointList.subList(0, pointListSize));
            population.add(chromosome);
        }

        return population;
    }
}