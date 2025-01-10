// TourController.java
package openerp.openerpresourceserver.controller;

import lombok.AllArgsConstructor;
import openerp.openerpresourceserver.converter.TourConverter;
import openerp.openerpresourceserver.dto.TourDTO;
import openerp.openerpresourceserver.dto.request.AddDestinationToTourRequest;
import openerp.openerpresourceserver.entity.Tour;
import openerp.openerpresourceserver.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/tour")
public class TourController {
    private final TourService tourService;
    private final TourConverter tourConverter;

    @GetMapping("/get-all")
    public ResponseEntity<List<TourDTO>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        List<TourDTO> tourDTOs = tours.stream()
                .map(tourConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(tourDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long id) {
        Tour tour = tourService.getTourById(id);
        TourDTO tourDTO = tourConverter.convertToDTO(tour);
        return ResponseEntity.ok().body(tourDTO);
    }

    @PostMapping("/add-destination")
    public ResponseEntity<Void> addDestinationToTour(@RequestBody AddDestinationToTourRequest request) {
        tourService.addDestinationToTour(request.getTourId(), request.getDestinationId());
        return ResponseEntity.ok().build();
    }
}