package nicolagraziani.U5_W3_D1_Spring_security.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "L'email è un campo obbligatorio e non può essere una Stringa vuota")
        @Email(message = "L'email inserita non è nel formato corretto")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        String password
) {
}
