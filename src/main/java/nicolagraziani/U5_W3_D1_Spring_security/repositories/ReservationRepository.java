package nicolagraziani.U5_W3_D1_Spring_security.repositories;


import nicolagraziani.U5_W3_D1_Spring_security.entities.Employee;
import nicolagraziani.U5_W3_D1_Spring_security.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByEmployeeAndTravel_TravelDate(Employee employee, LocalDate travelDate);
}
