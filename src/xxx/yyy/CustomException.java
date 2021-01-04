package xxx.yyy;

public class CustomException extends RuntimeException {
    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public CustomException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    public CustomException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }
    public CustomException(RuntimeException e) {
        message = e.getMessage();
    }
    public String getMessage() {
        return message;
    }
}
