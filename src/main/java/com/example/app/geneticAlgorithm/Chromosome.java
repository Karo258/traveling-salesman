package com.example.app.geneticAlgorithm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Chromosome {

    private List<Point> pointList;

    public Chromosome(Integer size) {
        pointList = new ArrayList<>(size);
    }
}
