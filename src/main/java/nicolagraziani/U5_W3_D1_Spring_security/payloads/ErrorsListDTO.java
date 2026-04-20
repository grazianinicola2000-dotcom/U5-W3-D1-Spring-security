package nicolagraziani.U5_W3_D1_Spring_security.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsListDTO(String message, LocalDateTime timestamp, List<String> errors) {
}
