// DestinationService.java
package openerp.openerpresourceserver.service;

import openerp.openerpresourceserver.entity.Destination;

import java.util.List;

public interface DestinationService {
    List<Destination> getAllDestinations();

    Destination createDestination(Destination destination);
}