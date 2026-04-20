package nicolagraziani.U5_W3_D1_Spring_security.repositories;


import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmplyeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
