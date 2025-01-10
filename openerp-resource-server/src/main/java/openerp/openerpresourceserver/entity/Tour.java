package openerp.openerpresourceserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tpd_tour")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "duration")
    private int duration;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "agency_id", referencedColumnName = "id")
    private Agency agency;

    @OneToMany(mappedBy = "tour")
    @JsonManagedReference
    private List<DesInTour> destinations;

    @OneToMany(mappedBy = "tour")
    @JsonManagedReference
    private List<FavList> favLists;

    @OneToMany(mappedBy = "tour")
    @JsonManagedReference
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "tour")
    @JsonManagedReference
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "tour")
    @JsonManagedReference
    private List<Bill> bills;
}