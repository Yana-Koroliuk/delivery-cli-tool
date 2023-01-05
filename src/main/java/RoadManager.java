import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RoadManager {
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
