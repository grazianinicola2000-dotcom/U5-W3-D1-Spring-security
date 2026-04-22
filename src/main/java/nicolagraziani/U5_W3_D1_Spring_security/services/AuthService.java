package nicolagraziani.U5_W3_D1_Spring_security.services;

import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.NotFoundException;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.UnauthorizedException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.LoginDTO;
import nicolagraziani.U5_W3_D1_Spring_security.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenTools tokenTools;
    private final EmployeeService employeeService;
    private final PasswordEncoder bcrypt;

    public AuthService(TokenTools tokenTools, EmployeeService employeeService, PasswordEncoder bcrypt) {
        this.tokenTools = tokenTools;
        this.employeeService = employeeService;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialAndGenerateToken(LoginDTO body) {

        try {
            this.employeeService.existsByEmail(body.email());
            Employee found = this.employeeService.findByEmail(body.email());

            if (this.bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali Errate, riprova");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali Errate, riprovare");
        }
    }


}
