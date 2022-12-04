import java.util.List;

public class City {
    // todo: read about POJO and data class
    // todo: add getters and setters
    // todo: add access modifiers to fields
    // add builder if needed
    // start using this class in main
    int id;
    String name;
    List<Connection> connections;
    public City(int id, String name, List<Connection> connections) {
        this.id = id;
        this.name = name;
        this.connections = connections;
    }
}
