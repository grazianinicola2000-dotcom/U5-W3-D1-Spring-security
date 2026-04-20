package nicolagraziani.U5_W3_D1_Spring_security.services;

import lombok.extern.slf4j.Slf4j;
import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.BadRequestException;
import nicolagraziani.U5_W3_D1_Spring_security.exceptions.NotFoundException;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.EmployeeDTO;
import nicolagraziani.U5_W3_D1_Spring_security.repositories.EmplyeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {

    private EmplyeeRepository emplyeeRepository;

    public EmployeeService(EmplyeeRepository emplyeeRepository) {
        this.emplyeeRepository = emplyeeRepository;
    }

    //    SAVE EMPLOYEE
    public Employee saveEmployee(EmployeeDTO body) {
        if (this.emplyeeRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'indirizzo mail " + body.email() + " è già in uso!");
        }
        if (this.emplyeeRepository.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");
        }
        Employee newEmployee = new Employee(body.username(), body.name(), body.surname(), body.email());
        this.emplyeeRepository.save(newEmployee);
        log.info("Il dipendente {} {} è stato registrato correttamente", body.surname(), body.name());
        return newEmployee;
    }

    //    FIND ALL EMPLOYEE WITH PAGINATION
    public Page<Employee> findAll(int page, int size, String sortBy) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.emplyeeRepository.findAll(pageable);
    }

    //    FIND BY ID
    public Employee findEmployeeById(UUID employeeId) {
        return this.emplyeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    //    FIND BY ID AND UPDATE
    public Employee findEmployeeByIdAndUpdate(UUID employeeId, EmployeeDTO body) {
        Employee found = this.findEmployeeById(employeeId);
        if (!found.getEmail().equals(body.email())) {
            if (this.emplyeeRepository.existsByEmail(body.email()))
                throw new BadRequestException("L'indirizzo email " + body.email() + " è già in uso!");
        }
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setUsername(body.username());

        Employee saved = this.emplyeeRepository.save(found);
        log.info("Il dipendente {} {} è stato modificato con successo", saved.getName(), saved.getSurname());
        return saved;
    }

    //    DELETE
    public void findEmployeeByIdAndDelete(UUID employeeId) {
        Employee found = this.findEmployeeById(employeeId);
        this.emplyeeRepository.delete(found);
        log.info("Il dipendente {} {} è stato eliminato correttamente", found.getSurname(), found.getName());
    }

}
