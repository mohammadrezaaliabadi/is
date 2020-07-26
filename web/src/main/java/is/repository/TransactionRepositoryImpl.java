package is.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository, Closeable {
    @Autowired
    private EntityManager<Transaction, Integer> entityManager;

    @Override
    public Transaction findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public Transaction save(Transaction t) {
        return entityManager.save(t);
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public Transaction update(Integer id, Transaction t) {
        return entityManager.update(id, t);
    }

    @Override
    public List<Transaction> find(Predicate<Transaction> predicate) {
        return find(predicate);

    }

    @Override
    public Transaction[] findAll() {
        return entityManager.findAll();
    }


    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Transaction, Integer>) entityManager).close();
        }
    }
}
