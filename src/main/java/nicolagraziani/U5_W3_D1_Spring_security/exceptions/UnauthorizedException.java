package nicolagraziani.U5_W3_D1_Spring_security.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
