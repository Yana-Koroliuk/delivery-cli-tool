package com.koroliuk.delivery_cli_tool.router;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class RouterTest {

    @Test
    public void findOptimalWayWithDijkstraAlgorithm() {
        List<List<List<Integer>>> adj = List.of(
                List.of(
                        List.of(1, 12),
                        List.of(2, 20)
                ),
                List.of(
                        List.of(0, 12)
                ),
                List.of(
                        List.of(0, 20)
                )
        );

        List<String> cityNames = List.of("A", "B", "C");
        DbManager dbManager = new DbManager();
        Router router = new Router(dbManager);
        router.setAdjacencyList(adj);
        router.setCityNameList(cityNames);

        String source = "A";
        String destination = "C";
        String actual = router.findOptimalWayWithDijkstraAlgorithm(source, destination);
        String expected = """
                Shortest path length is: 20
                Path is : [A]-[C]
                """;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void convertOptimalWayToString() {
        DbManager dbManager = new DbManager();
        Router router = new Router(dbManager);
        int wayLength = 12;
        List<Integer> path = Arrays.asList(2, 0, 1);
        String expected = """
                Shortest path length is: 12
                Path is : [A]-B-[C]
                """;

        String actual = router.convertOptimalWayToString(path, wayLength);

        Assert.assertEquals(expected, actual);
    }
}