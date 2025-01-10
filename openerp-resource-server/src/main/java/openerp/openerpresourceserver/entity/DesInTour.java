package openerp.openerpresourceserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tpd_des_in_tour")
public class DesInTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "des_id", referencedColumnName = "id")
    private Destination destination;
}