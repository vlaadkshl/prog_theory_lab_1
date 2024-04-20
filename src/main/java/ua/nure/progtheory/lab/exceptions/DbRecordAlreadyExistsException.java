package ua.nure.progtheory.lab.exceptions;

public class DbRecordAlreadyExistsException extends RuntimeException {

    public DbRecordAlreadyExistsException(String message) {
        super(message);
    }
}
