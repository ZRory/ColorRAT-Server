package eu.aragonapp.colorrat.utils;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright (c) 2018 Mythic Inc. (http://www.mythic.com/) All Rights Reserved.
 * <p>
 * Mythic Inc. licenses this file to you under the Apache License,
 * @Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * @https://www.apache.org/licenses/LICENSE-2.0
 */
public class Logger {

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    private final LineReader reader;
    private final Terminal terminal;

    private BufferedWriter writer;
    private final File file;

    public Logger(LineReader reader, Terminal terminal) {
        this.terminal = terminal;
        this.reader = reader;

        this.file = new File("logs", DATE_FORMAT.format(new Date()).replace(":", ".") + ".log");

        try {
            if (!this.file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            this.writer = new BufferedWriter(new FileWriter(this.file));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void info(String message) {
        final String res = "[INFO: " + Thread.currentThread().getName() + "/" + DATE_FORMAT.format(new Date()) + "] " + message;
        tryElseWrite(res);
        write(res);
    }

    public void error(String message) {
        final String res = "[ERROR: " + Thread.currentThread().getName() + "/" + DATE_FORMAT.format(new Date()) + "] " + message;
        tryElseWrite(res);
        write(res);
    }

    public void log(String message) {
        write(message);
    }

    private void tryElseWrite(String message) {
        try {
            reader.callWidget(LineReader.CLEAR);
            terminal.writer().println(message);
            reader.callWidget(LineReader.REDRAW_LINE);
            reader.callWidget(LineReader.REDISPLAY);
        } catch (Exception ex) {
            System.out.println(message);
        }
    }

    private void write(String message) {
        try {
            writer.write(message + "\r\n");
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
