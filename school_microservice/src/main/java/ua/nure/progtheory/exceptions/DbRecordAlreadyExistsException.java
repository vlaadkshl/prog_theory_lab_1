package ua.nure.progtheory.exceptions;

public class DbRecordAlreadyExistsException extends RuntimeException {

    public DbRecordAlreadyExistsException(String message) {
        super(message);
    }
}
