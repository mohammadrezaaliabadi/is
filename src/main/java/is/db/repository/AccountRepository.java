package is.db.repository;


import is.db.model.Account;

import java.util.List;
import java.util.function.Predicate;

public interface AccountRepository {
    Account findById(Integer id);
    void save(Account t);
    void delete(Integer id);
    void update(Integer id,Account t);
    List<Account> find(Predicate<Account> predicate);

}
