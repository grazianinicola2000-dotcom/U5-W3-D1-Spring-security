package nicolagraziani.U5_W3_D1_Spring_security.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
