package eu.aragonapp.colorrat.command;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.types.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class CommandManager {

    private final List<Command> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();

        this.commands.add(new ListenCommand());
        this.commands.add(new SelectCommand());
        this.commands.add(new BrowseCommand());
        this.commands.add(new BuildCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new ExitCommand());
        this.commands.add(new ListCommand());
    }

    public void check(String message) {
        String[] args = message.split(" ");

        try {
            String[] finalArgs = args;
            final Command command = this.commands.stream().filter(cmd -> finalArgs[0].equalsIgnoreCase(cmd.name())).findFirst().get();
            args = Arrays.copyOfRange(args, 1, args.length);

            if (!command.execute(args))
                ColorServer.getLogger().error(command.usage());
        } catch (Exception ex) {
            ColorServer.getLogger().info("If you need help type \"help\" into the console.");
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
