package openerp.openerpresourceserver.entity;

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
@Table(name = "tpd_agency")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private char phoneNumber;

    @Column(name = "password", nullable = false)
    private char password;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;
    
    @OneToMany(mappedBy = "agency")
    @JsonManagedReference
    private List<Tour> tours;

    public enum State {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }
}