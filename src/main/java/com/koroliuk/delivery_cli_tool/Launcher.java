package com.koroliuk.delivery_cli_tool;

import com.koroliuk.delivery_cli_tool.router.Router;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Launcher {

    private final Router router;

    public Launcher(Router router) {
        this.router = router;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(", ");
            try {
                if (Objects.equals(inputs[0], "quit") && inputs.length == 1) {
                    break;
                } else if (Objects.equals(inputs[0], "add") && inputs.length == 5) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String cityFrom = inputs[2];
                    String cityTo = inputs[3];
                    int weight = Integer.parseInt(inputs[4]);
                    router.addEdge(isOriented, cityFrom, cityTo, weight);
                } else if (Objects.equals(inputs[0], "delete") && inputs.length == 4) {
                    boolean isOriented = Integer.parseInt(inputs[1]) == 1;
                    String cityFrom = inputs[2];
                    String cityTo = inputs[3];
                    router.deleteEdge(isOriented, cityFrom, cityTo);
                } else if (Objects.equals(inputs[0], "calc") && inputs.length == 3) {
                    String cityFrom = inputs[1];
                    String cityTo = inputs[2];
                    System.out.println(router.findOptimalWayWithDijkstraAlgorithm(cityFrom, cityTo));
                } else {
                    System.out.println("Please write again");
                }
            } catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }
}
