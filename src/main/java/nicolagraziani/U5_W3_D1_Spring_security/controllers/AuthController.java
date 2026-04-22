package nicolagraziani.U5_W3_D1_Spring_security.controllers;

import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.ValidationException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.EmployeeDTO;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.LoginDTO;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.LoginResponseDTO;
import nicolagraziani.U5_W3_D1_Spring_security.services.AuthService;
import nicolagraziani.U5_W3_D1_Spring_security.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmployeeService employeeService;


    public AuthController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        return this.employeeService.saveEmployee(body);
    }
}
