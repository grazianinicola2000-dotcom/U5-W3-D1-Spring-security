package nicolagraziani.U5_W3_D1_Spring_security.services;

import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.NotFoundException;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.UnauthorizedException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.LoginDTO;
import nicolagraziani.U5_W3_D1_Spring_security.security.TokenTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenTools tokenTools;
    private final EmployeeService employeeService;

    public AuthService(TokenTools tokenTools, EmployeeService employeeService) {
        this.tokenTools = tokenTools;
        this.employeeService = employeeService;
    }

    public String checkCredentialAndGenerateToken(LoginDTO body) {

        try {
            this.employeeService.existsByEmail(body.email());
            Employee found = this.employeeService.findByEmail(body.email());

            if (found.getPassword().equals(body.password())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("Credenziali Errate, riprova");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("Credenziali Errate, riprovare");
        }
    }


}
