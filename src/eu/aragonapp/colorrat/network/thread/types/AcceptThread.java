package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.thread.ColorThread;

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
public class AcceptThread extends ColorThread {

    public AcceptThread() {
        super("Accept-Thread");
    }

    @Override
    public void update() {
        if(!ColorServer.getInstance().isRunning()) {
            this.close();
            return;
        }

        try {
            final Socket socket = ColorServer.getInstance().getSocket().accept();

            if(socket == null) return;
            socket.setKeepAlive(true);

            System.out.println("New Connection!");

            final NetworkConnection connection = new NetworkConnection(socket);
            ColorServer.getInstance().getClients().add(connection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
