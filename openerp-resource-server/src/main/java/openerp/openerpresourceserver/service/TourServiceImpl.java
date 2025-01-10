// TourServiceImpl.java
package openerp.openerpresourceserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import openerp.openerpresourceserver.entity.DesInTour;
import openerp.openerpresourceserver.entity.Destination;
import openerp.openerpresourceserver.entity.Tour;
import openerp.openerpresourceserver.repo.DesInTourRepo;
import openerp.openerpresourceserver.repo.DestinationRepo;
import openerp.openerpresourceserver.repo.TourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class TourServiceImpl implements TourService {

    private TourRepo tourRepo;

    private DesInTourRepo desInTourRepo;

    private DestinationRepo destinationRepo;

    @Override
    public List<Tour> getAllTours() {
        return tourRepo.findAll();
    }

    @Override
    public Tour getTourById(Long id) {
        return tourRepo.findById(String.valueOf(id)).orElse(null);
    }

    @Override
    public void addDestinationToTour(Long tourId, Long destinationId) {
        Tour tour = tourRepo.findById(String.valueOf(tourId)).orElseThrow(() -> new RuntimeException("Tour not found"));
        Destination destination = destinationRepo.findById(String.valueOf(destinationId)).orElseThrow(() -> new RuntimeException("Destination not found"));

        DesInTour desInTour = new DesInTour();
        desInTour.setTour(tour);
        desInTour.setDestination(destination);

        desInTourRepo.save(desInTour);
    }
}