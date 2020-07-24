package is.service;

import is.db.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
public interface CustomerService {
    Customer findById(Integer id);
    void save(Customer t);
    void delete(Integer id);
    void update(Integer id,Customer t);
    List<Customer> find(Predicate<Customer> predicate);
}
