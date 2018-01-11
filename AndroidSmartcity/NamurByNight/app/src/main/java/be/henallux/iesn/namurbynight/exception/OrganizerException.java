package be.henallux.iesn.namurbynight.exception;

public class OrganizerException extends Exception {
    private String exception;

    public OrganizerException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return this.exception;
    }
}
