package nicolagraziani.U5_W3_D1_Spring_security.exceptions;

import java.util.List;
import java.util.UUID;

public class NotFoundException extends RuntimeException {
    private List<String> errors;

    public NotFoundException(UUID id) {
        super("La risorsa con id " + id + " non è stata trovata");
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(List<String> errors) {
        super("Errori di validazione");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
