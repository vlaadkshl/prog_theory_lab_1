package ua.nure.progtheory.lab.business;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Teacher implements Serializable {
    Long id;
    String name;
}