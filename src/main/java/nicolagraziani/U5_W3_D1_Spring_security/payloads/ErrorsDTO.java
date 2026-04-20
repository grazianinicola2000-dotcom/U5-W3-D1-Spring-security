package nicolagraziani.U5_W3_D1_Spring_security.payloads;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
