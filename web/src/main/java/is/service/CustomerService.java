package is.service;


import is.domain.Customer;
import is.web.model.CustomerDto;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto);
    CustomerDto findById(int id);
    CustomerDto updateCustomer(int id,CustomerDto customerDto);
    void deleteCustomer(int id);
    List<CustomerDto> findAll();
    List<CustomerDto> findByLastName(String lastName);
}
