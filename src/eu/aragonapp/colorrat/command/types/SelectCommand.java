package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.NetworkConnection;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class SelectCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        final AtomicReference<NetworkConnection> client = new AtomicReference<>();
        ColorServer.getInstance().getClients().stream().filter(networkConnection -> networkConnection.getUid().equals(args[0])).findFirst().ifPresent(client::set);

        if (client.get() == null) {
            ColorServer.getLogger().error("The given uid \"" + args[0] + "\" doesn't exists!");
            return true;
        }

        final NetworkConnection networkConnection = client.get();

        ColorServer.getInstance().setSelectedConnection(networkConnection);
        ColorServer.getLogger().info("Successfully selected \"@" + networkConnection.getUsername().toLowerCase()+ "/" + networkConnection.getUid() + "\" as victim.");
        return true;
    }

    @Override
    public String description() {
        return "Selects a specified connection to control";
    }

    @Override
    public String usage() {
        return "select <uid>";
    }

    @Override
    public String name() {
        return "select";
    }

}
