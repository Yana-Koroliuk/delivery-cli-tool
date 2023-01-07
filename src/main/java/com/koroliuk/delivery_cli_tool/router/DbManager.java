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
    private static final String DB_PATH = "/home/koroliuk/IdeaProjects/delivery-cli-tool/src/main/resources/db.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public void update(List<List<List<Integer>>> adjacencyList, List<String> cityNameList) {
        Roads roads = new Roads();
        roads.setRoads(new ArrayList<>());
        for (int i = 0; i < adjacencyList.size(); i++) {
            List<List<Integer>> vertexList = adjacencyList.get(i);
            for (List<Integer> vertexLength : vertexList) {
                Road road = new Road(cityNameList.get(i), cityNameList.get(vertexLength.get(0)), vertexLength.get(1));
                roads.getRoads().add(road);
            }
        }
        try {
            File file = new File(DB_PATH);
            mapper.writeValue(file, roads);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //todo: може видалити roads і все робити з лістом
    public Roads init() {
        try {
            Path path = Paths.get(DB_PATH);
            String content = Files.readString(path);
            return mapper.readValue(content, Roads.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
