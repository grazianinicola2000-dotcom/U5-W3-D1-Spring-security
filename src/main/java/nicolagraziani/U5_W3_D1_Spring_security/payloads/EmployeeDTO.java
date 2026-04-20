package nicolagraziani.U5_W3_D1_Spring_security.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotBlank(message = "L'Username è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, max = 13, message = "L'username deve essere compreso tra 2 e 13 caratteri")
        String username,
        @NotBlank(message = "Il nome è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, max = 30, message = "Il nome deve essere compreso tra 2 e 30 caratteri")
        String name,
        @NotBlank(message = "Il cognome è un campo obbligatorio e non può essere una Stringa vuota")
        @Size(min = 2, max = 30, message = "Il cognome deve essere compreso tra 2 e 30 caratteri")
        String surname,
        @NotBlank(message = "L'email è un campo obbligatorio e non può essere una Stringa vuota")
        @Email(message = "L'email inserita non è nel formato corretto")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 4, message = "La password deve avere almeno 4 caratteri")
        // @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere almeno una maiuscola, una minuscola,....")
        String password
) {
}
