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
@Table(name = "tpd_bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "total_amount")
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "des_list")
    private String desList;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public enum PaymentStatus {
        PENDING, PAID, CANCELLED
    }
}