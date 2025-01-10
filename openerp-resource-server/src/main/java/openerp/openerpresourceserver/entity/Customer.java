package openerp.openerpresourceserver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tpd_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "bod")
    private Date bod;

    @Column(name = "job")
    private String job;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<FavList> favLists;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Bill> bills;
}