package com.koroliuk.delivery_cli_tool;

import com.koroliuk.delivery_cli_tool.launcher.Launcher;
import com.koroliuk.delivery_cli_tool.router.DbManager;
import com.koroliuk.delivery_cli_tool.router.Router;

public class App {

    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        Router router = new Router(dbManager);
        Launcher launcher = new Launcher(router);
        launcher.run();
    }
}
