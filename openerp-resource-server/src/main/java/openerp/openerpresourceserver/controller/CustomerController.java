// CustomerController.java
package openerp.openerpresourceserver.controller;

import lombok.AllArgsConstructor;
import openerp.openerpresourceserver.converter.CustomerConverter;
import openerp.openerpresourceserver.dto.CustomerDTO;
import openerp.openerpresourceserver.entity.Customer;
import openerp.openerpresourceserver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerConverter customerConverter;

    @GetMapping("/get-all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = customerConverter.convertToDTO(customer);
        return ResponseEntity.ok().body(customerDTO);
    }
}