// TourConverter.java
package openerp.openerpresourceserver.converter;

import openerp.openerpresourceserver.dto.DestinationDTO;
import openerp.openerpresourceserver.dto.TourDTO;
import openerp.openerpresourceserver.entity.DesInTour;
import openerp.openerpresourceserver.entity.Tour;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourConverter {

    private final ModelMapper modelMapper;
    private final DestinationConverter destinationConverter;

    @Autowired
    public TourConverter(ModelMapper modelMapper, DestinationConverter destinationConverter) {
        this.modelMapper = modelMapper;
        this.destinationConverter = destinationConverter;
    }

    public TourDTO convertToDTO(Tour tour) {
        TourDTO tourDTO = modelMapper.map(tour, TourDTO.class);
        List<DestinationDTO> destinationDTOs = tour.getDestinations().stream()
                .map(DesInTour::getDestination)
                .map(destinationConverter::convertToDto)
                .collect(Collectors.toList());
        tourDTO.setDestinations(destinationDTOs);
        return tourDTO;
    }
}