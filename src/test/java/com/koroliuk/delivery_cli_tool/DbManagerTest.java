package com.koroliuk.delivery_cli_tool;

import com.koroliuk.delivery_cli_tool.router.DbManager;
import com.koroliuk.delivery_cli_tool.router.Router;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class DbManagerTest {
    @Mock
    DbManager dbManager = Mockito.mock(DbManager.class);

    @Test
    public void update() {
        DbManager dbManager1 = new DbManager();
        Router router = new Router(dbManager1);
        router.loadInitialData();
        List<List<List<Integer>>> mockAdjacencyList = router.getAdjacencyList();
        List<String> mockCityNameList = router.getCityNameList();
        dbManager.update(mockAdjacencyList, mockCityNameList);
        Mockito.verify(dbManager).update(mockAdjacencyList, mockCityNameList);
    }

    @Test
    public void init() {
        dbManager.init();
        Mockito.verify(dbManager).init();
    }
}