package is.repository;


import is.domain.Account;
import is.domain.Customer;

import java.util.List;
import java.util.function.Predicate;

public interface CustomerRepository {
    Customer findById(Integer id);

    Customer save(Customer t);

    void delete(Integer id);

    Customer update(Integer id, Customer t);

    List<Customer> find(Predicate<Customer> predicate);
    List<Customer> findAll();

    Customer findByFirstName(String firstName);
    Customer findByLastName(String lastName);
    Customer findByNationalNumber(String nationalNumber);
}
