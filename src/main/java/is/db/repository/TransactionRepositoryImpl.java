package is.db.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.meta.Table;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.rw.bytes.SeekByteRW;
import is.db.model.Transaction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class TransactionRepositoryImpl implements TransactionRepository {
    private EntityManager<Transaction,Integer> entityManager;
    public TransactionRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Transaction,Integer> seekByteRW = new SeekByteRW(path, Transaction.class, new TypeReference<List<Transaction>>(){});
            this.entityManager = new EntityManagerImpl<>(seekByteRW,table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Transaction findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public void save(Transaction t) {
        try {
            entityManager.save(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public void update(Integer id, Transaction t) {
        entityManager.update(id,t);
    }

    @Override
    public List<Transaction> find(Predicate<Transaction> predicate) {
        return null;
    }
}
