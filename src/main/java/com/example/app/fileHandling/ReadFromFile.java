package com.example.app.fileHandling;

import com.example.app.geneticAlgorithm.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ReadFromFile {

    private String filename;

    public static List<Point> read(String filename) {
        File file = new File(filename);
        List<Point> pointList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isNodeCoordSection = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("NODE_COORD_SECTION")) {
                    isNodeCoordSection = true;
                    continue;
                }

                if (isNodeCoordSection) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 3) {
                        try {
                            int cityId = Integer.parseInt(parts[0]);
                            Double x = Double.parseDouble(parts[1]);
                            Double y = Double.parseDouble(parts[2]);
                            boolean alreadyInPointList = false;
                            for (Point point : pointList) {
                                if (point.getX().equals(x) && point.getY().equals(y)) {
                                    alreadyInPointList = true;
                                    break;
                                }
                            }
                            if (!alreadyInPointList) {
                                pointList.add(new Point(cityId, x, y));
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("File format exception.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("The file doesn't exist.");
        }
        return pointList;
    }
}