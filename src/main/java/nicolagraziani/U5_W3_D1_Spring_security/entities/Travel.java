package nicolagraziani.U5_W3_D1_Spring_security.entities;

import jakarta.persistence.*;
import lombok.*;
import nicolagraziani.U5_W3_D1_Spring_security.enums.TravelState;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "travels")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Travel {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID travelId;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false, name = "travel_date")
    private LocalDate travelDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TravelState state;

    public Travel(String destination, LocalDate travelDate, TravelState state) {
        this.destination = destination;
        this.travelDate = travelDate;
        this.state = state;
    }
}
