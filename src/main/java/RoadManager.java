import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RoadManager {
    public static void main(String[] args) {
        init();
    }

//    поле де є шлях до файла

    //todo: read initial data from json file and save it to adj matrix
    public static void init(List<List<List<Integer>>> adjacencyList, List<String> cityList) {
        try {
            Path path = Paths.get("/home/koroliuk/IdeaProjects/delivery-cli-tool/src/main/resources/db.json");
            String content = Files.readString(path);
            ObjectMapper mapper = new ObjectMapper();
            Roads roads = mapper.readValue(content, Roads.class);

//        зчитати файл
//        є в jackson object mapper
//        створити клас ребра чи дороги

//        оновити матрицю
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
