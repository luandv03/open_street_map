// DestinationConverter.java
package openerp.openerpresourceserver.converter;

import openerp.openerpresourceserver.dto.DestinationDTO;
import openerp.openerpresourceserver.entity.Destination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DestinationConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Destination convertToEntity(DestinationDTO destinationDTO) {
        return modelMapper.map(destinationDTO, Destination.class);
    }

    public DestinationDTO convertToDto(Destination destination) {
        return modelMapper.map(destination, DestinationDTO.class);
    }
}