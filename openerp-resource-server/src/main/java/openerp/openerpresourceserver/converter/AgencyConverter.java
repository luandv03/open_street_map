// AgencyConverter.java
package openerp.openerpresourceserver.converter;

import openerp.openerpresourceserver.dto.AgencyDTO;
import openerp.openerpresourceserver.entity.Agency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgencyConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public AgencyConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AgencyDTO convertToDTO(Agency agency) {
        return modelMapper.map(agency, AgencyDTO.class);
    }
}