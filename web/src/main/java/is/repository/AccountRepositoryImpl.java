package is.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
@Repository
public class AccountRepositoryImpl implements AccountRepository ,Closeable{
    @Autowired
    private EntityManager<Account, Integer> entityManager;

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
    public Account[] findAll() {
        return entityManager.findAll();
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
