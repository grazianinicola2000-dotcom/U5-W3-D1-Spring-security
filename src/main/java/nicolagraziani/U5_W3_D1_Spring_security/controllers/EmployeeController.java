package nicolagraziani.U5_W3_D1_Spring_security.controllers;


import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.ValidationException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.EmployeeDTO;
import nicolagraziani.U5_W3_D1_Spring_security.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    //    GET BY ID
    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId) {
        return this.employeeService.findEmployeeById(employeeId);
    }

    //    PUT
    @PutMapping("/{employeeId}")
    public Employee getEmployeeByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    //    DELETE
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getEmployeeByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findEmployeeByIdAndDelete(employeeId);
    }

}
