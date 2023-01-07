package com.koroliuk.delivery_cli_tool;

import java.util.ArrayList;
import java.util.List;

public class Router {
    public static List<List<List<Integer>>> adjacencyList = new ArrayList<>();
    public static final List<String> cityList = new ArrayList<>();
    private static final DbManager dbManager = new DbManager();



    public List<Integer> DijkstraAlgorithm(String source, String destination) {
        int idSource = cityList.indexOf(source);
        int idDestination = cityList.indexOf(destination);
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
                List<Integer> vertexWeights = adjacencyList.get(v).get(j);
                int to = vertexWeights.get(0);
                int len = vertexWeights.get(1);
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
        int pathLenght = distance.get(idDestination);
        path.add(pathLenght);
        return path;
    }

    public void addEdge(boolean isOriented, String CityFrom, String CityTo, int length) {
        if (cityList.contains(CityFrom) && cityList.contains(CityTo)) {
            deleteEdge(isOriented, CityFrom, CityTo);
        }
        if (!cityList.contains(CityFrom)) {
            cityList.add(CityFrom);
        }
        if (!cityList.contains(CityTo)) {
            cityList.add(CityTo);
        }
        int vertexFrom = cityList.indexOf(CityFrom);
        int vertexTo = cityList.indexOf(CityTo);
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
        dbManager.update();
    }

    public void deleteEdge(boolean isOriented, String source, String destination) {
        deleteDirection(source, destination);
        if (!isOriented) {
            deleteDirection(destination, source);
        }
        dbManager.update();
    }

    public void deleteDirection(String CityFrom, String CityTo) {
        int vertexFrom = cityList.indexOf(CityFrom);
        int vertexTo = cityList.indexOf(CityTo);
        List<List<Integer>> adjEdges = adjacencyList.get(vertexFrom);
        for (int i = 0; i < adjEdges.size(); i++) {
            List<Integer> vertexLength = adjEdges.get(i);
            if (vertexLength.get(0) == vertexTo) {
                adjEdges.remove(vertexLength);
            }
        }
    }

    public void printShortestDistance(List<Integer> path) {
        if ((path.get(path.size() - 1) != Integer.MAX_VALUE)) {
            System.out.println("Shortest path length is: " + path.get(path.size() - 1));
            System.out.print("Path is : [" + cityList.get(path.get(path.size() - 2)) + "]-");
            for (int i = path.size() - 3; i >= 1; i--) {
                System.out.print(cityList.get(path.get(i)) + "-");
            }
            System.out.println("[" + cityList.get(path.get(0)) + "]");
        } else {
            System.out.println("Given source and destination are not connected");

        }
    }

}
