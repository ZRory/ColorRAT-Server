package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.packet.types.server.S01PacketPing;
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
public class DisconnectThread extends ColorThread {

    public DisconnectThread() {
        super("Disconnect-Thread");
    }

    @Override
    public void update() {
        if (!ColorServer.getInstance().isRunning()) this.close();

        ColorServer.getInstance().getClients().forEach(clientInformations -> {
            if (!ColorServer.getInstance().isRunning()) {
                clientInformations.stop();
                return;
            }

            if (!(clientInformations.write(new S01PacketPing())))
                clientInformations.stop();
        });
    }

}
