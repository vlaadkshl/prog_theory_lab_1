package ua.nure.progtheory.lab.business;

import lombok.Builder;
import lombok.Data;
import ua.nure.progtheory.lab.data.GroupData;

import java.io.Serializable;

@Data
@Builder
public class Lesson implements Serializable {
    Long id;
    Teacher teacher;
    Subject subject;
    GroupData group;
}