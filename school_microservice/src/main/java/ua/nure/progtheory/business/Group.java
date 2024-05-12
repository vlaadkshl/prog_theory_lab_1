package ua.nure.progtheory.business;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Group implements Serializable {
    Long id;
    String name;
}