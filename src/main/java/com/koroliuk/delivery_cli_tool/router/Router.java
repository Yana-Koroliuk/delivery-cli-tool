package com.koroliuk.delivery_cli_tool.router;

import com.koroliuk.delivery_cli_tool.model.Road;
import com.koroliuk.delivery_cli_tool.model.Roads;

import java.util.*;

public class Router {
    public List<List<List<Integer>>> adjacencyList = new ArrayList<>();
    public List<String> cityNameList = new ArrayList<>();
    private final DbManager dbManager;

    public Router(DbManager dbManager) {
        this.dbManager = dbManager;
        loadInitialData(dbManager);
    }
    public List<List<List<Integer>>> getAdjacencyList() {
        return adjacencyList;
    }
    public void setAdjacencyList(List<List<List<Integer>>> adjacencyList)  {
        this.adjacencyList = adjacencyList;
    }

    public List<String> getCityNameList() {
        return cityNameList;
    }
    public void setCityNameList(List<String> cityNameList) {
        this.cityNameList = cityNameList;
    }

    public void loadInitialData(DbManager dbManager) {
        Roads roads = dbManager.init();
        List<Road> roadList = roads.getRoads();
        if (roadList != null) {
            for (Road road : roadList) {
                addEdgeToAdjList(true, road.getSource(), road.getDestination(), road.getLength());
            }
        }
    }

    public String findOptimalWayWithDijkstraAlgorithm(String source, String destination) {
        int idSource = cityNameList.indexOf(source);
        int idDestination = cityNameList.indexOf(destination);
        int vertexCount = adjacencyList.size();
        List<Integer> predecessor = new ArrayList<>();
        List<Integer> distance = new ArrayList<>();
        List<Boolean> visited = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            visited.add(i, false);
            distance.add(i, Integer.MAX_VALUE);
            predecessor.add(i, -1);
        }

        distance.set(idSource, 0);
        for (int i = 0; i < vertexCount; ++i) {
            int v = -1;
            for (int j = 0; j < vertexCount; ++j) {
                if (!visited.get(j) && (v == -1 || distance.get(j) < distance.get(v))) {
                    v = j;
                }
            }
            if (distance.get(v) == Integer.MAX_VALUE || v == idDestination) {
                break;
            }
            visited.set(v, true);
            for (int j = 0; j < adjacencyList.get(v).size(); ++j) {
                List<Integer> vertexInfo = adjacencyList.get(v).get(j);
                int to = vertexInfo.get(0);
                int len = vertexInfo.get(1);
                if (distance.get(v) + len < distance.get(to)) {
                    distance.set(to, distance.get(v) + len);
                    predecessor.set(to, v);
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        for (int v = idDestination; v != idSource; v = predecessor.get(v)) {
            if (v != -1) {
                path.add(v);
            } else {
                break;
            }
        }
        path.add(idSource);
        int pathLength = distance.get(idDestination);
        path.add(pathLength);
        return convertOptimalWayToString(path);
    }

    private void addEdgeToAdjList(boolean isOriented, String CityFrom, String CityTo, int length) {
        if (cityNameList.contains(CityFrom) && cityNameList.contains(CityTo)) {
            deleteEdge(isOriented, CityFrom, CityTo);
        }
        if (!cityNameList.contains(CityFrom)) {
            cityNameList.add(CityFrom);
        }
        if (!cityNameList.contains(CityTo)) {
            cityNameList.add(CityTo);
        }
        int vertexFrom = cityNameList.indexOf(CityFrom);
        int vertexTo = cityNameList.indexOf(CityTo);
        int size = adjacencyList.size();
        int maxVertex = Math.max(vertexFrom, vertexTo);
        if (maxVertex > size - 1) {
            for (int n = size; n <= maxVertex; n++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        List<Integer> vertexLength = new ArrayList<>();
        vertexLength.add(vertexTo);
        vertexLength.add(length);
        adjacencyList.get(vertexFrom).add(vertexLength);
        if (!isOriented) {
            vertexLength = new ArrayList<>();
            vertexLength.add(vertexFrom);
            vertexLength.add(length);
            adjacencyList.get(vertexTo).add(vertexLength);
        }
    }

    public void addEdge(boolean isOriented, String CityFrom, String CityTo, int length) {
        addEdgeToAdjList(isOriented, CityFrom, CityTo, length);
        dbManager.update(adjacencyList, cityNameList);
    }

    public void deleteEdge(boolean isOriented, String source, String destination) {
        deleteDirection(source, destination);
        if (!isOriented) {
            deleteDirection(destination, source);
        }
        dbManager.update(adjacencyList, cityNameList);
    }

    public void deleteDirection(String CityFrom, String CityTo) {
        int vertexFrom = cityNameList.indexOf(CityFrom);
        int vertexTo = cityNameList.indexOf(CityTo);
        List<List<Integer>> adjEdges = adjacencyList.get(vertexFrom);
        for (int i = 0; i < adjEdges.size(); i++) {
            List<Integer> vertexLength = adjEdges.get(i);
            if (vertexLength.get(0) == vertexTo) {
                adjEdges.remove(vertexLength);
            }
        }
    }

    public String convertOptimalWayToString(List<Integer> path) {
        String result = "";
        if ((path.get(path.size() - 1) != Integer.MAX_VALUE)) {
            result += "Shortest path length is: " + path.get(path.size() - 1) + "\n";
            result += "Path is : [" + cityNameList.get(path.get(path.size() - 2)) + "]-";
            for (int i = path.size() - 3; i >= 1; i--) {
                result += cityNameList.get(path.get(i)) + "-";
            }
            result += "[" + cityNameList.get(path.get(0)) + "]" + "\n";
            return result;
        } else {
            return "Given source and destination are not connected";
        }
    }

}
