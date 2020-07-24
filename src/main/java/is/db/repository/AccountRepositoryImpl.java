package is.db.repository;


import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.model.Account;
import is.db.rw.bytes.SeekByteRW;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class AccountRepositoryImpl implements AccountRepository {
    private EntityManager<Account,Integer> entityManager;

    public AccountRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Account,Integer> seekByteRW = new SeekByteRW(path, Account.class, new TypeReference<List<Account>>(){});
            this.entityManager = new EntityManagerImpl<>(seekByteRW,table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public void save(Account t) {
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
    public void update(Integer id, Account t) {
        entityManager.update(id,t);
    }

    @Override
    public List<Account> find(Predicate<Account> predicate) {
        //todo impl
        return null;
    }
}