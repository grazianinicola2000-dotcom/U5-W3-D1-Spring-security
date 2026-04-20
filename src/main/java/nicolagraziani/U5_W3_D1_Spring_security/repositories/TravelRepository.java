package nicolagraziani.U5_W3_D1_Spring_security.repositories;


import nicolagraziani.U5_W3_D1_Spring_security.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TravelRepository extends JpaRepository<Travel, UUID> {
}
