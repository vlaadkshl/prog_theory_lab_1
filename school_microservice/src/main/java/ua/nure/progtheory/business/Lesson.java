package ua.nure.progtheory.business;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Lesson implements Serializable {
    Long id;
    Teacher teacher;
    Subject subject;
    Group group;
}