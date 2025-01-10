// DestinationServiceImpl.java
package openerp.openerpresourceserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import openerp.openerpresourceserver.entity.Destination;
import openerp.openerpresourceserver.repo.DestinationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class DestinationServiceImpl implements DestinationService {

    private DestinationRepo destinationRepo;

    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepo.findAll();
    }

    @Override
    public Destination createDestination(Destination destination) {
        return destinationRepo.save(destination);
    }
}