// TourService.java
package openerp.openerpresourceserver.service;

import openerp.openerpresourceserver.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> getAllTours();

    Tour getTourById(Long id);

    void addDestinationToTour(Long tourId, Long destinationId);

}