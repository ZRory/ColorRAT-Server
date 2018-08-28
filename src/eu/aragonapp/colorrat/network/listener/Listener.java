package eu.aragonapp.colorrat.network.listener;

import eu.aragonapp.colorrat.network.thread.types.AcceptThread;

import java.net.Socket;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class Listener {

    private final AcceptThread acceptThread;
    private final int port;

    public Listener(int port) {
        this.port = port;

        this.acceptThread = new AcceptThread(this);
        this.acceptThread.start();
    }

    public void close() {
        this.acceptThread.close();

        try {
            final Socket socket = new Socket("127.0.0.1", this.port);
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

}
