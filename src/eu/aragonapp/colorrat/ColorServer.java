package eu.aragonapp.colorrat;

import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.thread.types.AcceptThread;
import eu.aragonapp.colorrat.network.thread.types.DisconnectThread;
import eu.aragonapp.colorrat.utils.$;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

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

    private final List<NetworkConnection> clients;

    private final DisconnectThread disconnectThread;
    private final AcceptThread acceptThread;

    private ServerSocket socket;
    private boolean running;

    public ColorServer() {
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
        this.disconnectThread = new DisconnectThread();
        this.disconnectThread.start();
    }


    public List<NetworkConnection> getClients() {
        return clients;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public static ColorServer getInstance() {
        return instance;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public boolean isRunning() {
        return running;
    }

}
