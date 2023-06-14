package com.example.app.geneticAlgorithm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Chromosome implements Comparable<Chromosome> {

    private List<Point> pointList;
    private Double fitness;
    private Double rank;

    public Chromosome(Integer size) {
        pointList = new ArrayList<>(size);
    }

    public Chromosome(List<Point> list) {
        this.pointList = list;
    }

    @Override
    public int compareTo(Chromosome otherChromosome) {
        return Double.compare(this.fitness, otherChromosome.fitness);
    }

    public Point getPoint(int index) {
        return pointList.get(index);
    }

}
