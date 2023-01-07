package com.koroliuk.delivery_cli_tool;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class DbManagerTest {
    @Mock
    DbManager dbManager = Mockito.mock(DbManager.class);

    @Test
    public void update() {
        dbManager.update();
        Mockito.verify(dbManager).update();
    }

    @Test
    public void init() {
        dbManager.init();
        Mockito.verify(dbManager).init();
    }
}