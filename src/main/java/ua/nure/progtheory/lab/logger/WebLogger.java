package ua.nure.progtheory.lab.logger;

import java.time.LocalDateTime;

public interface WebLogger {

    void log(LocalDateTime timeOfRequest,
             String requestType,
             String requestDetails);
}
