package openerp.openerpresourceserver.repo;

import openerp.openerpresourceserver.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepo extends JpaRepository<Tour, String> {
}
