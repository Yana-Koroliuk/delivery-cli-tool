package com.koroliuk.delivery_cli_tool.launcher;

import com.koroliuk.delivery_cli_tool.router.Router;

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
            String command = inputs[0];
            try {
                if ("quit".equals(command) && inputs.length == 1) {
                    break;
                } else if ("add".equals(command) && inputs.length == 5) {
                    boolean isOriented = "1".equals(inputs[1]);
                    String cityFrom = inputs[2];
                    String cityTo = inputs[3];
                    int length = Integer.parseInt(inputs[4]);
                    router.addRoad(isOriented, cityFrom, cityTo, length);
                } else if ("delete".equals(command) && inputs.length == 4) {
                    boolean isOriented = "1".equals(inputs[1]);
                    String cityFrom = inputs[2];
                    String cityTo = inputs[3];
                    router.deleteRoad(isOriented, cityFrom, cityTo);
                } else if ("calc".equals(command) && inputs.length == 3) {
                    String cityFrom = inputs[1];
                    String cityTo = inputs[2];
                    String optimalWay = router.findOptimalWayWithDijkstraAlgorithm(cityFrom, cityTo);
                    System.out.print(optimalWay);
                } else {
                    System.out.println("Please write again!");
                }
            } catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }
}
