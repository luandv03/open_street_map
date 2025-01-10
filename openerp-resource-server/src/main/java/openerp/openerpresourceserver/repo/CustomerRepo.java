package openerp.openerpresourceserver.repo;

import openerp.openerpresourceserver.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, String> {
}
