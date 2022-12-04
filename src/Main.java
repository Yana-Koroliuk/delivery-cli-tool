import java.util.*;

// todo: rename if possible i and j that is out of loop
public class Main {
    public static void main(String[] args) {

        List<List<List<Integer>>> adjacencyList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();

        System.out.println(adjacencyList);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            try {
                if (Objects.equals(inputs[0], "quit") && inputs.length == 1) {
                    break;
                } else if (Objects.equals(inputs[0], "add") && inputs.length == 5) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String CityFrom = inputs[2];
                    String CityTo = inputs[3];
                    int weight = Integer.parseInt(inputs[4]);
                    addEdge(adjacencyList, cityList, isOriented, CityFrom, CityTo, weight);
                    System.out.println(adjacencyList);
                } else if (Objects.equals(inputs[0], "delete") && inputs.length == 4) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String CityFrom = inputs[2];
                    String CityTo = inputs[3];
                    deleteEdge(adjacencyList, isOriented, cityList.indexOf(CityFrom), cityList.indexOf(CityTo));
                } else if (Objects.equals(inputs[0], "calc") && inputs.length == 3) {
                    String CityFrom = inputs[1];
                    String CityTo = inputs[2];
                    List<Integer> path = DijkstraAlgorithm(adjacencyList, cityList.indexOf(CityFrom), cityList.indexOf(CityTo));
                    printShortestDistance(path, cityList);
                } else {
                    System.out.println("Please write again");
                }
            }
            catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }

    private static void addEdge(List<List<List<Integer>>> adjacencyList, List<String> cityList, boolean isOriented, String CityFrom, String CityTo, int weight) {
        if (!cityList.contains(CityFrom)) {
            cityList.add(CityFrom);
        }
        if (!cityList.contains(CityTo)) {
            cityList.add(CityTo);
        }
        int i = cityList.indexOf(CityFrom);
        int j = cityList.indexOf(CityTo);
        int size = adjacencyList.size();
        int maxVertex = Math.max(i, j);
        if (maxVertex > size - 1) {
            for (int n = size; n <= maxVertex; n++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        List<Integer> vertexWeights = new ArrayList<>();
        vertexWeights.add(j);
        vertexWeights.add(weight);
        if (!adjacencyList.get(i).contains(vertexWeights)) {
            adjacencyList.get(i).add(vertexWeights);
            if (!isOriented) {
                vertexWeights = new ArrayList<>();
                vertexWeights.add(i);
                vertexWeights.add(weight);
                if (!adjacencyList.get(j).contains(vertexWeights)) {
                    adjacencyList.get(j).add(vertexWeights);
                }
            }
        }

    }

    private static void deleteEdge(List<List<List<Integer>>> adjacencyList, boolean isOriented, int source, int destination) {
        deleteDirection(adjacencyList, source, destination);
        if (!isOriented) {
            deleteDirection(adjacencyList, destination, source);
        }
        System.out.println(adjacencyList);
    }

    private static void deleteDirection(List<List<List<Integer>>> adjacencyList, int vertexFrom, int vertexTo) {
        List<List<Integer>> adjEdges = adjacencyList.get(vertexFrom);
        for (int i = 0; i < adjEdges.size(); i++) {
            List<Integer> vertexWeights = adjEdges.get(i);
            if (vertexWeights.contains(vertexTo) && vertexWeights.get(0) == vertexTo) {
                adjEdges.remove(vertexWeights);
            }
        }
    }


    private static List<Integer> DijkstraAlgorithm(List<List<List<Integer>>> adjacencyList, int source, int destination) {
        int vertexCount = adjacencyList.size();
        List<Integer> predecessor = new ArrayList<>();
        List<Integer> distance = new ArrayList<>();
        List<Boolean> visited = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            visited.add(i, false);
            distance.add(i, Integer.MAX_VALUE);
            predecessor.add(i, -1);
        }

        distance.set(source, 0);
        for (int i = 0; i < vertexCount; ++i) {
            int v = -1;
            for (int j = 0; j < vertexCount; ++j) {
                if (!visited.get(j) && (v == -1 || distance.get(j) < distance.get(v))) {
                    v = j;
                }
            }
            if (distance.get(v) == Integer.MAX_VALUE || v == destination) {
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
        for (int v = destination; v != source; v = predecessor.get(v)) {
            if (v != -1) {
                path.add(v);
            } else {
                break;
            }
        }
        path.add(source);
        int pathLenght = distance.get(destination);
        path.add(pathLenght);
        return path;
    }

    private static void printShortestDistance(List<Integer> path, List<String> cityList) {
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
