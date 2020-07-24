package is.service;

import is.config.Me;
import is.db.model.Customer;
import is.db.repository.CustomerRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@NoArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void save(Customer t) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, Customer t) {

    }

    @Override
    public List<Customer> find(Predicate<Customer> predicate) {
        return null;
    }
}
