package is.service;

import is.repository.CustomerRepository;
import is.web.mapper.CustomerMapper;
import is.web.model.CustomerDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CustomerMapper mapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        return mapper.customerToCustomerDto(repository.save(mapper.customerDtoToCustomer(customerDto)));
    }

    @Override
    public CustomerDto findById(int id) {
        return mapper.customerToCustomerDto(repository.findById(id));
    }

    @Override
    public CustomerDto updateCustomer(int id, CustomerDto customerDto) {
        return mapper.customerToCustomerDto(repository.update(id,mapper.customerDtoToCustomer(customerDto)));
    }

    @Override
    public void deleteCustomer(int id) {
        repository.delete(id);
    }

    @Override
    public List<CustomerDto> findAll() {
        return repository.findAll().stream().map(mapper::customerToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto findByFirstName(String firstName) {
        return mapper.customerToCustomerDto(repository.findByFirstName(firstName));
    }

    @Override
    public CustomerDto findByLastName(String lastName) {
        return mapper.customerToCustomerDto(repository.findByLastName(lastName));

    }

    @Override
    public CustomerDto findByNationalNumber(String nationalNumber) {
        return mapper.customerToCustomerDto(repository.findByNationalNumber(nationalNumber));
    }

}
