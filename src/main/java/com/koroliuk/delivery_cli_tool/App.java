package com.koroliuk.delivery_cli_tool;

import java.util.*;

public class App {

    public static void main(String[] args) {
        Router router = new Router();
        DbManager dbManager = new DbManager();
        dbManager.init();

        System.out.println(Router.adjacencyList);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(", ");
            try {
                if (Objects.equals(inputs[0], "quit") && inputs.length == 1) {
                    break;
                } else if (Objects.equals(inputs[0], "add") && inputs.length == 5) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String CityFrom = inputs[2];
                    String CityTo = inputs[3];
                    int weight = Integer.parseInt(inputs[4]);
                    router.addEdge(isOriented, CityFrom, CityTo, weight);
                    System.out.println(Router.adjacencyList);
                } else if (Objects.equals(inputs[0], "delete") && inputs.length == 4) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String CityFrom = inputs[2];
                    String CityTo = inputs[3];
                    router.deleteEdge(isOriented, CityFrom, CityTo);
                    System.out.println(Router.adjacencyList);
                } else if (Objects.equals(inputs[0], "calc") && inputs.length == 3) {
                    String CityFrom = inputs[1];
                    String CityTo = inputs[2];
                    List<Integer> path = router.DijkstraAlgorithm(CityFrom, CityTo);
                    router.printShortestDistance(path);
                } else {
                    System.out.println("Please write again");
                }
            } catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }
}
