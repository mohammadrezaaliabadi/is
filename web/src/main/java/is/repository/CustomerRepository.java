package is.repository;


import is.domain.Customer;

import java.util.List;
import java.util.function.Predicate;

public interface CustomerRepository {
    Customer findById(Integer id);

    Customer save(Customer t);

    void delete(Integer id);

    Customer update(Integer id, Customer t);

    List<Customer> find(Predicate<Customer> predicate);
}
