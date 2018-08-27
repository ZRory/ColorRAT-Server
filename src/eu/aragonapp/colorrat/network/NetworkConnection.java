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

    private ReceiveThread receiveThread;

    private final Socket socket;

    public NetworkConnection(Socket socket) {
        this.socket = socket;

        try {
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            this.stop();
            return;
        }

        this.receiveThread = new ReceiveThread(this);
        this.receiveThread.start();
    }

    public boolean write(Packet packet) {
        if(this.outputStream == null) throw new NullPointerException("Output Stream is null!");

        try {
            this.outputStream.writeObject(packet);
            this.outputStream.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Object readObject() throws Exception {
        if(this.inputStream == null) throw new NullPointerException("Input Stream is null!");

        return this.inputStream.readObject();
    }

    public void stop() {
        if(this.receiveThread != null)
            this.receiveThread.close();

        ColorServer.getInstance().getClients().remove(this);

        //TODO("Disconnect message")
    }

}
