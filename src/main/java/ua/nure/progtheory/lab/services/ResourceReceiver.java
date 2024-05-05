package ua.nure.progtheory.lab.services;

import java.util.List;

public interface ResourceReceiver<T> {

    List<T> getAll();

    T get(Long id);
}
