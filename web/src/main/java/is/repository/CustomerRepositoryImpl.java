package is.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Account;
import is.domain.Customer;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class CustomerRepositoryImpl implements CustomerRepository, Closeable {
    private EntityManager<Customer, Integer> entityManager;

    public CustomerRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Customer, Integer> seekByteRW = new SeekByteRW(path, table,Customer.class, new TypeReference<List<Customer>>() {
            });
            this.entityManager = new EntityManagerImpl<>(seekByteRW, table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return entityManager.save(customer);
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public Customer update(Integer id, Customer t) {
        return entityManager.update(id,t);
    }

    @Override
    public List<Customer> find(Predicate<Customer> predicate) {
        return find(predicate);
    }

    @Override
    public List<Customer> findAll() {
        return entityManager.findAllList();
    }

    @Override
    public Customer findByFirstName(String firstName) {
        return entityManager.findByIndex(firstName,"firstName");
    }

    @Override
    public Customer findByLastName(String lastName) {
        return entityManager.findByIndex(lastName,"lastName");

    }

    @Override
    public Customer findByNationalNumber(String nationalNumber) {
        return entityManager.findByIndex(nationalNumber,"nationalNumber");
    }

    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Customer, Integer>) entityManager).close();
        }
    }
}
