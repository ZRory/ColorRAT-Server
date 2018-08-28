package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.listener.Listener;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class ListenCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if(args.length == 1) {
            int port = 0;

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {
                ColorServer.getLogger().error("The specified port \"" + args[0] + "\" is not valid.");
                return true;
            }

            final Listener listener = new Listener(port);
            ColorServer.getInstance().getListeners().add(listener);
            ColorServer.getLogger().info("Successfully created a listener which is listening on " + args[0]);
        }
        return true;
    }

    @Override
    public String description() {
        return "Listens to a specified port for incoming connections";
    }

    @Override
    public String usage() {
        return "listen <port>";
    }

    @Override
    public String name() {
        return "listen";
    }

}
