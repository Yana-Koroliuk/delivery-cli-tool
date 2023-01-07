package com.koroliuk.delivery_cli_tool;

import com.koroliuk.delivery_cli_tool.router.DbManager;
import com.koroliuk.delivery_cli_tool.router.Router;
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
    public void findOptimalWayWithDijkstraAlgorithm() {
        DbManager dbManager = new DbManager();
        Router router = new Router(dbManager);
        router.loadInitialData(dbManager);
        String source = "A";
        String destination = "C";
        String actual = router.findOptimalWayWithDijkstraAlgorithm(source, destination);
        String expected = "Shortest path length is: 56\n" + "Path is : [A]-B-[C]" + "\n";
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
    public void convertOptimalWayToString() {
        DbManager dbManager = new DbManager();
        Router router = new Router(dbManager);
        List<Integer> path = Arrays.asList(2, 0, 1, 56);
        String actual = router.convertOptimalWayToString(path);
        String expected = "Shortest path length is: 56\n" + "Path is : [A]-B-[C]" + "\n";
        Assert.assertEquals(expected, actual);
    }
}