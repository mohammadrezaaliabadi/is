package is.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Account;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class AccountRepositoryImpl implements AccountRepository ,Closeable{
    private EntityManager<Account, Integer> entityManager;

    public AccountRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Account, Integer> seekByteRW = new SeekByteRW(path,table, Account.class, new TypeReference<List<Account>>() {
            });
            this.entityManager = new EntityManagerImpl<>(seekByteRW, table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public Account save(Account t) {
        return entityManager.save(t);
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public Account update(Integer id, Account t) {
        return entityManager.update(id, t);
    }

    @Override
    public List<Account> find(Predicate<Account> predicate) {
        return entityManager.find(predicate);
    }

    @Override
    public List<Account> findAll() {
        return entityManager.findAllList();
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return entityManager.findByIndex(accountNumber,"accountNumber");
    }


    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Account, Integer>) entityManager).close();
        }
    }
}
