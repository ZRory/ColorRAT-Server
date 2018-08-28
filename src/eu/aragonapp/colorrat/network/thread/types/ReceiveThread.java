package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.packet.Packet;
import eu.aragonapp.colorrat.network.thread.ColorThread;

import java.io.EOFException;
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
public class ReceiveThread extends ColorThread {

    private final NetworkConnection connection;

    public ReceiveThread(NetworkConnection connection) {
        super("Receive");

        this.connection = connection;
    }

    @Override
    public void run() {
        while (!this.connection.getSocket().isClosed() && ColorServer.getInstance().isRunning()) {
            try {
                final Object object = getInformations().getInputStream().readObject();
                if (!(object instanceof Packet)) return;
                ((Packet) object).execute(this.connection);
            } catch (SocketException | EOFException ex) {
                this.connection.stop();
            } catch (Exception ex) {
                ColorServer.getLogger().error("Couldn't handle packet: " + ex.getMessage());
            }
        }

        ColorServer.getLogger().info("Client disconnected.. (@" + this.connection.getUsername().toLowerCase() + "/" + connection.getSocket().getInetAddress().getHostAddress() + ":" + connection.getSocket().getPort() + ")");
        ColorServer.getInstance().getClients().remove(this.connection);
        this.close();
    }

    public NetworkConnection getInformations() {
        return connection;
    }

}
