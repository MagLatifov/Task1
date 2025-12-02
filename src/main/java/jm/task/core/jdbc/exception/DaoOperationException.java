package jm.task.core.jdbc.exception;

public class DaoOperationException extends RuntimeException {
    private final String operation;
    public DaoOperationException(String operation, String message, Throwable cause) {
        super(message, cause);
        this.operation = operation;
    }
    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "Произошла ошибка при операции: " +operation + " | " + getMessage();
    }
}
