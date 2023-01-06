import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class RoadManager {

    public void initUpdate() {
        Roads roads = new Roads();
        roads.setRoads(new ArrayList<>());
        for(int i = 0; i < Main.adjacencyList.size(); i++) {
            for(int j = 0; j < Main.adjacencyList.get(i).size(); j++) {
                Road road = new Road(Main.cityList.get(i), Main.cityList.get(Main.adjacencyList.get(i).get(j).get(0)), Main.adjacencyList.get(i).get(j).get(1));
                roads.getRoads().add(road);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String DB_PATH = "/home/koroliuk/IdeaProjects/delivery-cli-tool/src/main/resources/db.json";
        Path path = Paths.get(DB_PATH);
        try {
            File file = new File(path.toString());
            mapper.writeValue(file, roads);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        String DB_PATH = "/home/koroliuk/IdeaProjects/delivery-cli-tool/src/main/resources/db.json";
        try {
            Path path = Paths.get(DB_PATH);
            String content = Files.readString(path);
            Roads roads = mapper.readValue(content, Roads.class);

            for(Road road : roads.getRoads()) {
                Main.addEdge(true, road.getSource(), road.getDestination(), road.getLength());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
