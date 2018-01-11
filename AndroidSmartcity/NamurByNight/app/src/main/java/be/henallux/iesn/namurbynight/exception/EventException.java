package be.henallux.iesn.namurbynight.exception;

public class EventException extends Exception {
    private String exception;

    public EventException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return this.exception;
    }
}