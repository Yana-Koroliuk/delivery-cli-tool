import java.util.List;

public class City {
    private int id;
    private String name;
    private List<Road> connections;
    public City(int id, String name, List<Road> connections) {
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

    public List<Road> getConnections() {
        return connections;
    }

    public void setConnections(List<Road> connections) {
        this.connections = connections;
    }
}
