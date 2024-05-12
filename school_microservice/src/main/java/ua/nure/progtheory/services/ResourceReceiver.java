package ua.nure.progtheory.services;

import java.util.List;

public interface ResourceReceiver<T> {

    List<T> getAll();

    T get(Long id);
}
