package com.koroliuk.delivery_cli_tool.model;

import com.koroliuk.delivery_cli_tool.model.Road;

import java.util.List;

public class Roads {
    private List<Road> roads;

    public Roads() {
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public List<Road> getRoads() {
        return this.roads;
    }

}
