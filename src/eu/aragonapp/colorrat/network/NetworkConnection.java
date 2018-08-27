package eu.aragonapp.colorrat.network;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.packet.Packet;
import eu.aragonapp.colorrat.network.thread.types.ReceiveThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class NetworkConnection implements Serializable {

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final Socket socket;

    public NetworkConnection(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.socket = socket;

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

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public Socket getSocket() {
        return socket;
    }

}
