public class Connection {
    private City source;
    private City destination;
    private int time;

    public Connection(City source, City destination, int time) {
        this.source = source;
        this.destination = destination;
        this.time = time;
    }

    public City getSource() {
        return source;
    }

    public void setSource(City source) {
        this.source = source;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
