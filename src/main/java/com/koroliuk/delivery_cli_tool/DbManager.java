package com.koroliuk.delivery_cli_tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koroliuk.delivery_cli_tool.model.Road;
import com.koroliuk.delivery_cli_tool.model.Roads;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class DbManager {
    private static final String DB_PATH = "/home/koroliuk/IdeaProjects/delivery-cli-tool/src/main/resources/db.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public DbManager() {
        init();
    }


    public void update() {
        Roads roads = new Roads();
        roads.setRoads(new ArrayList<>());
        for(int i = 0; i < Router.adjacencyList.size(); i++) {
            //List<List<Integer>>  = com.koroliuk.delivery_cli_tool.App.adjacencyList.get(i);
            for(int j = 0; j < Router.adjacencyList.get(i).size(); j++) {
                Road road = new Road(Router.cityList.get(i), Router.cityList.get(Router.adjacencyList.get(i).get(j).get(0)), Router.adjacencyList.get(i).get(j).get(1));
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
    public void init() {
        try {
            Path path = Paths.get(DB_PATH);
            String content = Files.readString(path);
            Roads roads = mapper.readValue(content, Roads.class);

            for(Road road : roads.getRoads()) {
                Router.addEdge(true, road.getSource(), road.getDestination(), road.getLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
