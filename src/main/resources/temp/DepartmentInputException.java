package temp;

public class DepartmentInputException extends Exception{

    public DepartmentInputException() {
        super();
    }

    public DepartmentInputException(String message) {
        super(message);
    }

    public DepartmentInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentInputException(Throwable cause) {
        super(cause);
    }

    protected DepartmentInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
