package eu.aragonapp.colorrat.network.thread.types;

import eu.aragonapp.colorrat.ColorServer;
import eu.aragonapp.colorrat.network.thread.ColorThread;
import eu.aragonapp.colorrat.utils.Logger;
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
public class ConsoleThread extends ColorThread {

    public ConsoleThread() {
        super("Console");
    }

    @Override
    public void run() {
        try {
            final String prompt = "> ";

            final TerminalBuilder builder = TerminalBuilder.builder();
            final Terminal terminal = builder.build();

            final LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();

            ColorServer.setLogger(new Logger(reader, terminal));
            ColorServer.getLogger().info(" _____     _         _____ _____ _____           _____                     ");
            ColorServer.getLogger().info("|     |___| |___ ___| __  |  _  |_   _|   ___   |   __|___ ___ _ _ ___ ___ ");
            ColorServer.getLogger().info("|   --| . | | . |  _|    -|     | | |    |___|  |__   | -_|  _| | | -_|  _|");
            ColorServer.getLogger().info("|_____|___|_|___|_| |__|__|__|__| |_|           |_____|___|_|  \\_/|___|_| ");
            ColorServer.getLogger().info("                                                Version 1.0 by Timo Behrend");
            ColorServer.getLogger().info("If you need help type \"\033[4;1mhelp\033[0m\" into the console.");

            String line;
            while ((line = reader.readLine(prompt, prompt, (MaskingCallback) null, null)) != null) {
                if (line == null) return;

                ColorServer.getLogger().log(prompt + line);
                ColorServer.getInstance().getCommandManager().check(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
