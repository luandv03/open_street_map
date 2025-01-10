// CustomerService.java
package openerp.openerpresourceserver.service;

import openerp.openerpresourceserver.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

}