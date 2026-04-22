package nicolagraziani.U5_W3_D1_Spring_security.controllers;


import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.ValidationException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.EmployeeDTO;
import nicolagraziani.U5_W3_D1_Spring_security.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //    GET ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "surname") String sortBy) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    //    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.employeeService.saveEmployee(body);
    }

    @GetMapping("/me")
    public Employee getOwnProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        return currentAuthenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee updateOwnProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody EmployeeDTO body) {
        return this.employeeService.findEmployeeByIdAndUpdate(currentAuthenticatedEmployee.getEmployeeId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        this.employeeService.findEmployeeByIdAndDelete(currentAuthenticatedEmployee.getEmployeeId());
    }

    //    GET BY ID
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeeService.findEmployeeById(employeeId);
    }

    //    PUT
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public Employee getEmployeeByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    //    DELETE
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getEmployeeByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findEmployeeByIdAndDelete(employeeId);
    }

}
