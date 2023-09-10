package thiagoDRangel.exceptions;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    public BusinessException(String message) {
        super(message);
    }
}
