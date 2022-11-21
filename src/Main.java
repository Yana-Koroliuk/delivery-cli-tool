import java.util.*;

public class Main {
    public static void main(String[] args) {
        int INF = 1000000000;
        int vertex = 11;
        List<List<List<Integer>>> adjacencyList = new ArrayList<>(vertex);
        for (int i = 0; i < vertex; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        addEdge(adjacencyList, 0, 1, 2, 1);
        addEdge(adjacencyList, 0, 2, 3, 2);
        addEdge(adjacencyList, 0, 2, 5, 3);
        addEdge(adjacencyList, 0, 4, 6, 3);
        addEdge(adjacencyList, 0, 3, 4, 5);
        addEdge(adjacencyList, 0, 3, 7, 1);
        addEdge(adjacencyList, 0, 5, 6, 4);
        addEdge(adjacencyList, 0, 7, 8, 2);
        addEdge(adjacencyList, 0, 8, 9, 10);
        System.out.println(adjacencyList);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.charAt(0) == 'q') {
                break;
            } else if (input.charAt(0) == 'a') {
                List<String> inputs = Arrays.asList(input.split(" "));
                int orient = Integer.parseInt(inputs.get(1));
                int i = Integer.parseInt(inputs.get(2));
                int j = Integer.parseInt(inputs.get(3));
                int weight = Integer.parseInt(inputs.get(4));
                addEdge(adjacencyList, orient, i, j, weight);
                System.out.println(adjacencyList);
            } else if (input.charAt(0) == 'd') {
                List<String> inputs = Arrays.asList(input.split(" "));
                int orient = Integer.parseInt(inputs.get(1));
                int i = Integer.parseInt(inputs.get(2));
                int j = Integer.parseInt(inputs.get(3));
                deleteEdge(adjacencyList, orient, i, j);
            } else if (input.charAt(0) == 'c') {
                List<String> inputs = Arrays.asList(input.split(" "));
                int source = Integer.parseInt(inputs.get(1));
                int destination = Integer.parseInt(inputs.get(2));
                printShortestDistance(adjacencyList, vertex, INF, source, destination);
            }
        }
    }

    private static void addEdge(List<List<List<Integer>>> adjacencyList, int orient, int i, int j, int weight) {
        List<Integer> vertexWeightList = new ArrayList<>();
        vertexWeightList.add(j);
        vertexWeightList.add(weight);
        adjacencyList.get(i).add(vertexWeightList);
        if (orient == 0) {
            vertexWeightList = new ArrayList<>();
            vertexWeightList.add(i);
            vertexWeightList.add(weight);
            adjacencyList.get(j).add(vertexWeightList);
        }
    }

    private static void deleteEdge(List<List<List<Integer>>> adjacencyList, int orient, int i, int j) {
        deleteDirection(adjacencyList, i, j);
        if (orient == 0) {
            deleteDirection(adjacencyList, j, i);
            System.out.println(adjacencyList);
        }
    }

    private static void deleteDirection(List<List<List<Integer>>> adjacencyList, int i, int j) {
        List<List<Integer>> adjEdges = adjacencyList.get(i);
        for (int m = 0; m < adjEdges.size(); m++) {
            if (adjEdges.get(m).contains(j) && adjEdges.get(m).get(0) == j) {
                adjEdges.remove(adjEdges.get(m));
            }
        }
    }

    private static List<Integer> DijkstraAlgorithm(List<List<List<Integer>>> adjacencyList, int vertex, int INF, int source, int destination) {
        int[] predecessor = new int[vertex];
        int[] distance = new int[vertex];
        boolean[] visited = new boolean[vertex];

        for (int i = 0; i < vertex; i++) {
            visited[i] = false;
            distance[i] = INF;
            predecessor[i] = -1;
        }

        distance[source] = 0;
        for (int i = 0; i < vertex; ++i) {
            int v = -1;
            for (int j = 0; j < vertex; ++j) {
                if (!visited[j] && (v == -1 || distance[j] < distance[v])) {
                    v = j;
                }
            }
            if (distance[v] == INF || v == destination) {
                break;
            }
            visited[v] = true;
            for (int j = 0; j < adjacencyList.get(v).size(); ++j) {
                int to = adjacencyList.get(v).get(j).get(0);
                int len = adjacencyList.get(v).get(j).get(1);
                if (distance[v] + len < distance[to]) {
                    distance[to] = distance[v] + len;
                    predecessor[to] = v;
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        for (int v = destination; v != source; v = predecessor[v]) {
            if (v != -1) {
                path.add(v);
            } else {
                break;
            }
        }
        path.add(source);
        int pathLenght = distance[destination];
        path.add(pathLenght);
        return path;
    }

    private static void printShortestDistance(List<List<List<Integer>>> adjacencyList, int vertex, int INF, int source, int destination) {
        List<Integer> path = DijkstraAlgorithm(adjacencyList, vertex, INF, source, destination);
        if ((path.get(path.size() - 1) != INF)) {
            System.out.println("Shortest path length is: " + path.get(path.size() - 1));
            System.out.print("Path is : [" + path.get(path.size() - 2) + "]-");
            for (int i = path.size() - 3; i >= 1; i--) {
                System.out.print(path.get(i) + "-");
            }
            System.out.println("[" + path.get(0) + "]");
        } else {
            System.out.println("Given source and destination are not connected");

        }
    }
}




