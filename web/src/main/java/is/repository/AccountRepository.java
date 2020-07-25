package is.repository;



import is.domain.Account;

import java.util.List;
import java.util.function.Predicate;

public interface AccountRepository {
    Account findById(Integer id);

    Account save(Account t);

    void delete(Integer id);

    Account update(Integer id, Account t);

    List<Account> find(Predicate<Account> predicate);
    List<Account> findAll();

    Account findByAccountNumber(String accountNumber);
}
