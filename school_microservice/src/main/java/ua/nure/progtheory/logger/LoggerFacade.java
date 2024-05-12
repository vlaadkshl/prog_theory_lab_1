package ua.nure.progtheory.logger;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggerFacade {
    private final WebRequestEventPublisher webRequestEventPublisher = new WebRequestEventPublisher();

    public LoggerFacade() {
        webRequestEventPublisher.addObserver(FileLogger.getInstance());
    }

    public void logRequest(String requestType, String requestDetails) {
        RequestEvent requestEvent = new RequestEvent(LocalDateTime.now(), requestType, requestDetails);
        webRequestEventPublisher.notifyObservers(requestEvent);
    }
}