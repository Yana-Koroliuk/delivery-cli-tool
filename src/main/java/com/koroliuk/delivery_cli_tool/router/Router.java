package com.koroliuk.delivery_cli_tool.router;

import com.koroliuk.delivery_cli_tool.model.Road;
import com.koroliuk.delivery_cli_tool.model.Roads;

import java.util.*;

public class Router {
    private List<List<List<Integer>>> adjacencyList = new ArrayList<>();
    private List<String> cityNameList = new ArrayList<>();
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
    private static final List<Integer> predecessor = new ArrayList<>();
    private static final List<Integer> distance = new ArrayList<>();
    private static final List<Boolean> visited = new ArrayList<>();

    public void prepareList(int cityAmount) {
        for (int i = 0; i < cityAmount; i++) {
            visited.add(false);
            distance.add(Integer.MAX_VALUE);
            predecessor.add(-1);
        }
    }

    public String findOptimalWayWithDijkstraAlgorithm(String source, String destination) {
        int idSource = cityNameList.indexOf(source);
        int idDestination = cityNameList.indexOf(destination);
        int cityAmount = adjacencyList.size();
        prepareList(cityAmount);

        distance.set(idSource, 0);
        for (int i = 0; i < cityAmount; ++i) {
            int cityId = -1;
            for (int j = 0; j < cityAmount; ++j) {
                if (!visited.get(j) && (cityId == -1 || distance.get(j) < distance.get(cityId))) {
                    cityId = j;
                }
            }
            if (distance.get(cityId) == Integer.MAX_VALUE || cityId == idDestination) {
                break;
            }
            visited.set(cityId, true);
            List<List<Integer>> cityRoads = adjacencyList.get(cityId);
            for (List<Integer> roadInfo : cityRoads) {
                int cityToId = roadInfo.get(0);
                int length = roadInfo.get(1);
                int distanceBetweenCity = distance.get(cityId) + length;
                if (distanceBetweenCity < distance.get(cityToId)) {
                    distance.set(cityToId, distanceBetweenCity);
                    predecessor.set(cityToId, cityId);
                }
            }
        }

        List<Integer> way = new ArrayList<>();
        for (int cityId = idDestination; cityId != idSource; cityId = predecessor.get(cityId)) {
            if (cityId == -1) break;
            way.add(cityId);
        }
        way.add(idSource);

        int wayLength = distance.get(idDestination);
        return convertOptimalWayToString(way, wayLength);
    }

    public void addRoad(boolean isOriented, String cityFrom, String cityTo, int length) {
        addRoadToAdjList(isOriented, cityFrom, cityTo, length);
        dbManager.update(adjacencyList, cityNameList);
    }

    public void deleteRoad(boolean isOriented, String cityFrom, String cityTo) {
        deleteRoadFromAdjList(isOriented, cityFrom, cityTo);
        dbManager.update(adjacencyList, cityNameList);
    }

    private void addRoadToAdjList(boolean isOriented, String cityFrom, String cityTo, int length) {
        if (cityNameList.contains(cityFrom) && cityNameList.contains(cityTo)) {
            deleteRoadFromAdjList(isOriented, cityFrom, cityTo);
        }
        if (!cityNameList.contains(cityFrom)) {
            cityNameList.add(cityFrom);
        }
        if (!cityNameList.contains(cityTo)) {
            cityNameList.add(cityTo);
        }
        int cityFromId = cityNameList.indexOf(cityFrom);
        int cityToId = cityNameList.indexOf(cityTo);
        int cityAmount = adjacencyList.size();
        int maxCityId = Math.max(cityFromId, cityToId);
        if (maxCityId > cityAmount - 1) {
            for (int n = cityAmount; n <= maxCityId; n++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        List<Integer> roadInfo = new ArrayList<>();
        roadInfo.add(cityToId);
        roadInfo.add(length);
        List<List<Integer>> cityFromRoads = adjacencyList.get(cityFromId);
        cityFromRoads.add(roadInfo);
        if (!isOriented) {
            List<Integer> secondDirectionRoadInfo = new ArrayList<>();
            secondDirectionRoadInfo.add(cityFromId);
            secondDirectionRoadInfo.add(length);
            List<List<Integer>> cityToRoads = adjacencyList.get(cityToId);
            cityToRoads.add(secondDirectionRoadInfo);
        }
    }

    private void deleteRoadFromAdjList(boolean isOriented, String cityFrom, String cityTo) {
        deleteDirection(cityFrom, cityTo);
        if (!isOriented) {
            deleteDirection(cityTo, cityFrom);
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
                    .append("]");

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
