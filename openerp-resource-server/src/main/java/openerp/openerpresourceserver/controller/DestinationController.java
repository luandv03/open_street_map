// DestinationController.java
package openerp.openerpresourceserver.controller;

import lombok.AllArgsConstructor;
import openerp.openerpresourceserver.converter.DestinationConverter;
import openerp.openerpresourceserver.dto.DestinationDTO;
import openerp.openerpresourceserver.entity.Destination;
import openerp.openerpresourceserver.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/destination")
public class DestinationController {
    private DestinationService destinationService;
    private DestinationConverter destinationConverter;

    @GetMapping("/get-all")
    public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
        List<Destination> destinations = destinationService.getAllDestinations();
        List<DestinationDTO> destinationDTOs = destinations.stream()
                .map(destinationConverter::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(destinationDTOs);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO destinationDTO) {
        Destination destination = destinationConverter.convertToEntity(destinationDTO);
        Destination newDestination = destinationService.createDestination(destination);
        DestinationDTO newDestinationDTO = destinationConverter.convertToDto(newDestination);
        return ResponseEntity.ok().body(newDestinationDTO);
    }
}