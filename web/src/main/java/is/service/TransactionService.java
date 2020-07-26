package is.service;

import is.domain.Transaction;
import is.web.model.AccountDto;
import is.web.model.TransactionDto;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {
    TransactionDto saveTransaction(TransactionDto transactionDto) throws NotFoundException;
    TransactionDto findById(int id);
    TransactionDto updateTransaction(int id,TransactionDto transactionDto);
    void deleteTransaction(int id);
    List<TransactionDto> findAll();
    List fromAccountTransaction() throws NoSuchFieldException, IllegalAccessException;
    List toAccountTransaction() throws NoSuchFieldException, IllegalAccessException;
}
