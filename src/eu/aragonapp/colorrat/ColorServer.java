package eu.aragonapp.colorrat;

import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.thread.types.AcceptThread;
import eu.aragonapp.colorrat.network.thread.types.ConsoleThread;
import eu.aragonapp.colorrat.utils.$;
import eu.aragonapp.colorrat.utils.Logger;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class ColorServer {

    private static ColorServer instance;
    private static Logger logger;

    private final ArrayList<NetworkConnection> clients;

    private final ConsoleThread consoleThread;
    private final AcceptThread acceptThread;

    private ServerSocket socket;
    private boolean running;

    public ColorServer() {
        LogManager.getLogManager().reset();

        instance = this;

        this.clients = new ArrayList<>();

        try {
            this.socket = new ServerSocket($.PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        this.setRunning(true);
        this.acceptThread = new AcceptThread();
        this.acceptThread.start();
        this.consoleThread = new ConsoleThread();
        this.consoleThread.start();
    }

    public static void setLogger(Logger logger) {
        ColorServer.logger = logger;
    }

    public ArrayList<NetworkConnection> getClients() {
        return clients;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static ColorServer getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public boolean isRunning() {
        return running;
    }

}
