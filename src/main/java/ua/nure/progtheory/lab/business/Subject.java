package ua.nure.progtheory.lab.business;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Subject implements Serializable {
    Long id;
    String name;
    List<Teacher> teachers;
}