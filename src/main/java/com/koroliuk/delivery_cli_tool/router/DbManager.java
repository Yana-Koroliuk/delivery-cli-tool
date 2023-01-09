package com.koroliuk.delivery_cli_tool.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koroliuk.delivery_cli_tool.model.Road;
import com.koroliuk.delivery_cli_tool.model.Roads;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DbManager {
    private static final String DB_PATH = "src/main/resources/db.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public void update(List<List<List<Integer>>> adjacencyList, List<String> cityNameList) {
        List<Road> roadList = new ArrayList<>();
        for (int i = 0; i < adjacencyList.size(); i++) {
            String cityFrom = cityNameList.get(i);
            List<List<Integer>> cityRoads = adjacencyList.get(i);
            for (List<Integer> roadInfo : cityRoads) {
                String cityTo = cityNameList.get(roadInfo.get(0));
                int length = roadInfo.get(1);
                Road road = new Road(cityFrom, cityTo, length);
                roadList.add(road);
            }
        }

        Roads roads = new Roads();
        roads.setRoads(roadList);

        try {
            File file = new File(DB_PATH);
            mapper.writeValue(file, roads);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Roads init() {
        try {
            Path path = Paths.get(DB_PATH);
            String content = Files.readString(path);
            return mapper.readValue(content, Roads.class);
        } catch (IOException e) {
            System.out.println("No file with initial data provided or it is empty!");
        }
        return null;
    }
}
