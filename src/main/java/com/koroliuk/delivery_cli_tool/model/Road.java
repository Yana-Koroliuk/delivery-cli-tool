package com.koroliuk.delivery_cli_tool.model;

public class Road {
    private String source;
    private String destination;
    private int length;

    public Road(String source, String destination, int length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public Road() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int time) {
        this.length = time;
    }
}
