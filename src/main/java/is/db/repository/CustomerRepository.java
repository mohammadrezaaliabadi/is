package is.db.repository;

import is.db.model.Customer;

import java.util.List;
import java.util.function.Predicate;

public interface CustomerRepository {
    Customer findById(Integer id);
    void save(Customer t);
    void delete(Integer id);
    void update(Integer id,Customer t);
    List<Customer> find(Predicate<Customer> predicate);

}
