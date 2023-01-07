package com.koroliuk.delivery_cli_tool;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;


public class RouterTest {
    @Mock
    Router router = Mockito.mock(Router.class);

    @Test
    public void dijkstraAlgorithm() {
        DbManager dbManager = new DbManager();
        dbManager.init();
        Router router = new Router();
        String source = "A";
        String destination = "C";
        List<Integer> actual = router.DijkstraAlgorithm(source, destination);
        List<Integer> expected = Arrays.asList(2, 0, 1, 56);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addEdge() {
        boolean isOriented = true;
        String CityFrom = "A";
        String CityTo = "B";
        int length = 12;
        router.addEdge(isOriented, CityFrom, CityTo, length);
        Mockito.verify(router).addEdge(isOriented, CityFrom, CityTo, length);
    }

    @Test
    public void deleteEdge() {
        boolean isOriented = true;
        String source = "A";
        String destination = "B";
        router.deleteEdge(isOriented, source, destination);
        Mockito.verify(router).deleteEdge(isOriented, source, destination);
    }

    @Test
    public void deleteDirection() {
        String CityFrom = "A";
        String CityTo = "B";
        router.deleteDirection(CityFrom, CityTo);
        Mockito.verify(router).deleteDirection(CityFrom, CityTo);
    }

    @Test
    public void printShortestDistance() {
        List<Integer> path = Arrays.asList(2, 0, 1, 56);
        router.printShortestDistance(path);
        Mockito.verify(router).printShortestDistance(path);
    }

}