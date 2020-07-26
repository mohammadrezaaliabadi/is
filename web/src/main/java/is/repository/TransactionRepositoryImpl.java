package is.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Transaction;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class TransactionRepositoryImpl implements TransactionRepository, Closeable {
    private EntityManager<Transaction, Integer> entityManager;

    public TransactionRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Transaction, Integer> seekByteRW = new SeekByteRW(path, table,Transaction.class, new TypeReference<List<Transaction>>() {
            });
            this.entityManager = new EntityManagerImpl<>(seekByteRW, table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public List<Transaction> findAll() {
        return entityManager.findAllList();
    }

    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Transaction, Integer>) entityManager).close();
        }
    }
}
