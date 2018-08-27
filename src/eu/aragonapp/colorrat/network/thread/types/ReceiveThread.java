package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.packet.Packet;
import eu.aragonapp.colorrat.network.thread.ColorThread;

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
        super("Receive(" + connection.hashCode() + ")-Thread");

        this.connection = connection;
    }

    @Override
    public void update() {
        if(!ColorServer.getInstance().isRunning()) {
            this.close();
            return;
        }

        try {
            final Object object = getInformations().readObject();
            if (!(object instanceof Packet)) return;

            ((Packet) object).execute(this.connection);
        } catch (Exception ex) {
            this.connection.stop();
        }
    }

    public NetworkConnection getInformations() {
        return connection;
    }

}
