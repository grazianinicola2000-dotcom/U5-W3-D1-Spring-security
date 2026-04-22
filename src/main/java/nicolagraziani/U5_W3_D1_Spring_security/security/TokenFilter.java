package nicolagraziani.U5_W3_D1_Spring_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.UnauthorizedException;
import nicolagraziani.U5_W3_D1_Spring_security.services.EmployeeService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final TokenTools tokenTools;
    private final EmployeeService employeeService;

    public TokenFilter(TokenTools tokenTools, EmployeeService employeeService) {
        this.tokenTools = tokenTools;
        this.employeeService = employeeService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire il token nell'authorization header nel formato corretto");
        }

        String accessToken = authHeader.replace("Bearer ", "");
        tokenTools.verifyToken(accessToken);

        //    AUTORIZZAZIONE
        UUID employeeId = this.tokenTools.extractIdFromToken(accessToken);
        Employee authenticatedEmployee = this.employeeService.findEmployeeById(employeeId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedEmployee, null, authenticatedEmployee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());

    }


}
