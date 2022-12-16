import java.util.List;

public class City {
    // todo: read about POJO and data class
    // DONE // todo: add getters and setters
    // DONE // todo: add access modifiers to fields
    // add builder if needed
    // start using this class in main
    private int id;
    private String name;
    private List<Connection> connections;
    public City(int id, String name, List<Connection> connections) {
        this.id = id;
        this.name = name;
        this.connections = connections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
