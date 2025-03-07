package ua.nure.progtheory.lab.logger;

import java.time.LocalDateTime;

public record RequestEvent(LocalDateTime timeOfRequest,
                           String requestType,
                           String requestDetails) {
}