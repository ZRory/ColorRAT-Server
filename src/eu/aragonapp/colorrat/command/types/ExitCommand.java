package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.listener.Listener;
import eu.aragonapp.colorrat.utils.$;

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
public class ExitCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        ColorServer.getInstance().setRunning(false);

        for (Listener listener : ColorServer.getInstance().getListeners())
            listener.close();
        return true;
    }

    @Override
    public String description() {
        return "Shut downs the program and closes all existing streams and connections";
    }

    @Override
    public String usage() {
        return "exit";
    }

    @Override
    public String name() {
        return "exit";
    }

}
