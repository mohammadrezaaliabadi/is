package is.repository;


import is.domain.Transaction;

import java.util.List;
import java.util.function.Predicate;

public interface TransactionRepository {
    Transaction findById(Integer id);

    Transaction save(Transaction t);

    void delete(Integer id);

    Transaction update(Integer id, Transaction t);

    List<Transaction> find(Predicate<Transaction> predicate);

    List<Transaction> findAll();
}
