package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class HelpCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 1) {

        } else if (args.length == 0) {
            for(Command command : ColorServer.getInstance().getCommandManager().getCommands())
                ColorServer.getLogger().info(command.usage() + " » " + command.description());
            return true;
        }

        return false;
    }

    @Override
    public String description() {
        return "Shows a list of all commands, which you can use";
    }

    @Override
    public String usage() {
        return "help <command>";
    }

    @Override
    public String name() {
        return "help";
    }

}
