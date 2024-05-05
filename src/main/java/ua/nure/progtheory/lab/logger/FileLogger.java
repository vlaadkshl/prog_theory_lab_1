package ua.nure.progtheory.lab.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileLogger implements WebLogger {
    private static FileLogger instance;

    private PrintWriter writer;

    private FileLogger() {
        try {
            FileWriter fileWriter = new FileWriter("log.txt", true);
            writer = new PrintWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileLogger getInstance() {
        if (instance == null) {
            instance = new FileLogger();
        }
        return instance;
    }

    @Override
    public void log(LocalDateTime timeOfRequest,
                    String requestType,
                    String requestDetails) {
        writer.println(timeOfRequest + " " + requestType + " " + requestDetails);
        writer.flush();
    }
}