import java.util.List;

public class City {
    // DONE // todo: read about POJO and data class
    // DONE // todo: add getters and setters
    // DONE // todo: add access modifiers to fields
    // add builder if needed
    // todo: start using this class in main
    // DONE // todo: write graph in file
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
