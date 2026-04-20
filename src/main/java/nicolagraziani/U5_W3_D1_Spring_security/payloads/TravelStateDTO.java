package nicolagraziani.U5_W3_D1_Spring_security.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TravelStateDTO(
        @NotBlank(message = "Lo stato è obbligatorio")
        @Pattern(regexp = "completed|programmed", message = "Lo stato può essere solo 'completed' o 'programmed'")
        String state
) {
}
