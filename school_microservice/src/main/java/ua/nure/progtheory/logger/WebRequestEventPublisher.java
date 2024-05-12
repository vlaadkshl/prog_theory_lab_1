package ua.nure.progtheory.logger;

import java.util.ArrayList;
import java.util.List;

public class WebRequestEventPublisher {
    private final List<WebLogger> listeners = new ArrayList<>();

    public void addObserver(WebLogger observer) {
        listeners.add(observer);
    }

    public void removeObserver(WebLogger observer) {
        listeners.remove(observer);
    }

    public void notifyObservers(RequestEvent requestEvent) {
        for (WebLogger listener : listeners) {
            listener.log(requestEvent.timeOfRequest(), requestEvent.requestType(), requestEvent.requestDetails());
        }
    }
}