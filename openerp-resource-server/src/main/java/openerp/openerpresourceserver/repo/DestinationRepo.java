package openerp.openerpresourceserver.repo;

import openerp.openerpresourceserver.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepo extends JpaRepository<Destination, String> {
}
