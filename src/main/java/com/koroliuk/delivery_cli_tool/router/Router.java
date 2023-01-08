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
        loadInitialData();
    }

    public void loadInitialData() {
        Roads roads = dbManager.init();
        if (roads != null) {
            List<Road> roadList = roads.getRoads();
            if (roadList != null) {
                for (Road road : roadList) {
                    addRoadToAdjList(true, road.getSource(), road.getDestination(), road.getLength());
                }
            }
        }
    }

    public String findOptimalWayWithDijkstraAlgorithm(String source, String destination) {
        int idSource = cityNameList.indexOf(source);
        int idDestination = cityNameList.indexOf(destination);
        int cityAmount = adjacencyList.size();
        List<Integer> predecessor = new ArrayList<>();
        List<Integer> distance = new ArrayList<>();
        List<Boolean> visited = new ArrayList<>();

        for (int i = 0; i < cityAmount; i++) {
            visited.add(false);
            distance.add(Integer.MAX_VALUE);
            predecessor.add(-1);
        }

        distance.set(idSource, 0);
        for (int i = 0; i < cityAmount; ++i) {
            int v = -1;
            for (int j = 0; j < cityAmount; ++j) {
                if (!visited.get(j) && (v == -1 || distance.get(j) < distance.get(v))) {
                    v = j;
                }
            }
            if (distance.get(v) == Integer.MAX_VALUE || v == idDestination) {
                break;
            }
            visited.set(v, true);
            List<List<Integer>> cityRoads = adjacencyList.get(v);
            for (List<Integer> vertexInfo : cityRoads) {
                int to = vertexInfo.get(0);
                int len = vertexInfo.get(1);
                if (distance.get(v) + len < distance.get(to)) {
                    distance.set(to, distance.get(v) + len);
                    predecessor.set(to, v);
                }
            }
        }

        List<Integer> way = new ArrayList<>();
        for (int v = idDestination; v != idSource; v = predecessor.get(v)) {
            if (v != -1) {
                way.add(v);
            } else {
                break;
            }
        }
        way.add(idSource);

        int wayLength = distance.get(idDestination);
        return convertOptimalWayToString(way, wayLength);
    }

    public void addRoad(boolean isOriented, String CityFrom, String CityTo, int length) {
        addRoadToAdjList(isOriented, CityFrom, CityTo, length);
        dbManager.update(adjacencyList, cityNameList);
    }

    public void deleteRoad(boolean isOriented, String source, String destination) {
        deleteRoadFromAdjList(isOriented, source, destination);
        dbManager.update(adjacencyList, cityNameList);
    }

    private void addRoadToAdjList(boolean isOriented, String CityFrom, String CityTo, int length) {
        if (cityNameList.contains(CityFrom) && cityNameList.contains(CityTo)) {
            deleteRoadFromAdjList(isOriented, CityFrom, CityTo);
        }
        if (!cityNameList.contains(CityFrom)) {
            cityNameList.add(CityFrom);
        }
        if (!cityNameList.contains(CityTo)) {
            cityNameList.add(CityTo);
        }
        int vertexFromId = cityNameList.indexOf(CityFrom);
        int vertexToId = cityNameList.indexOf(CityTo);
        int cityAmount = adjacencyList.size();
        int maxVertexId = Math.max(vertexFromId, vertexToId);
        if (maxVertexId > cityAmount - 1) {
            for (int n = cityAmount; n <= maxVertexId; n++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        List<Integer> roadInfo = new ArrayList<>();
        roadInfo.add(vertexToId);
        roadInfo.add(length);
        List<List<Integer>> cityRoads = adjacencyList.get(vertexFromId);
        cityRoads.add(roadInfo);
        if (!isOriented) {
            List<Integer> secondDirectionRoadInfo = new ArrayList<>(roadInfo);
            cityRoads.add(secondDirectionRoadInfo);
        }
    }

    private void deleteRoadFromAdjList(boolean isOriented, String source, String destination) {
        deleteDirection(source, destination);
        if (!isOriented) {
            deleteDirection(destination, source);
        }
    }

    public void deleteDirection(String cityFrom, String cityTo) {
        int cityFromId = cityNameList.indexOf(cityFrom);
        int cityToId = cityNameList.indexOf(cityTo);
        List<List<Integer>> cityFromRoads = adjacencyList.get(cityFromId);
        for (int i = 0; i < cityFromRoads.size(); i++) {
            List<Integer> roadInfo = cityFromRoads.get(i);
            int adjacencyCityId = roadInfo.get(0);
            if (adjacencyCityId == cityToId) {
                cityFromRoads.remove(roadInfo);
            }
        }
    }

    public String convertOptimalWayToString(List<Integer> way, int wayLength) {
        StringBuilder result = new StringBuilder();
        String sourceCityName = cityNameList.get(way.get(way.size() - 1));
        if (wayLength != Integer.MAX_VALUE) {
            result.append("Shortest path length is: ")
                    .append(wayLength)
                    .append("\n")
                    .append("Path is : [")
                    .append(sourceCityName)
                    .append("]-");

            for (int i = way.size() - 2; i >= 1; i--) {
                String cityName = cityNameList.get(way.get(i));
                result.append(cityName)
                        .append("-");
            }

            String destinationCityName = cityNameList.get(way.get(0));
            result.append("[")
                    .append(destinationCityName)
                    .append("]")
                    .append("\n");

            return result.toString();
        } else {
            return "Given source and destination are not connected";
        }
    }

    public List<List<List<Integer>>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<List<List<Integer>>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<String> getCityNameList() {
        return cityNameList;
    }

    public void setCityNameList(List<String> cityNameList) {
        this.cityNameList = cityNameList;
    }

}
