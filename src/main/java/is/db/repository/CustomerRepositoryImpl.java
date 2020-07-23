package is.db.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.meta.Table;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.rw.bytes.SeekByteRW;
import is.db.model.Customer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class CustomerRepositoryImpl implements CustomerRepository {
    private EntityManager<Customer,Integer> entityManager;

    public CustomerRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Customer,Integer> seekByteRW = new SeekByteRW(path, Customer.class, new TypeReference<List<Customer>>(){});
            this.entityManager = new EntityManagerImpl<>(seekByteRW,table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public void save(Customer customer) {
        try {
            entityManager.save(customer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public void update(Integer id, Customer t) {
        update(id, Customer.builder().build());
    }

    @Override
    public List<Customer> find(Predicate<Customer> predicate) {
        //todo impl
        return null;
    }
}
