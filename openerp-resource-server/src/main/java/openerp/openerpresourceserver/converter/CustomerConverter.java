// CustomerConverter.java
package openerp.openerpresourceserver.converter;

import openerp.openerpresourceserver.dto.CustomerDTO;
import openerp.openerpresourceserver.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CustomerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDTO convertToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
}