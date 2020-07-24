package is.repository;


import is.db.model.Transaction;

import java.util.List;
import java.util.function.Predicate;

public interface TransactionRepository {
    Transaction findById(Integer id);

    void save(Transaction t);

    void delete(Integer id);

    void update(Integer id, Transaction t);

    List<Transaction> find(Predicate<Transaction> predicate);
}
