package is.service;

import is.domain.Transaction;
import is.repository.TransactionRepository;
import is.web.mapper.TransactionMapper;
import is.web.model.AccountDto;
import is.web.model.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private TransactionMapper mapper;


    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        return mapper.transactionToTransactionDto(repository.save(mapper.transactionDtoToTransaction(transactionDto)));
    }

    @Override
    public TransactionDto findById(int id) {
        return mapper.transactionToTransactionDto(repository.findById(id));
    }

    @Override
    public TransactionDto updateTransaction(int id, TransactionDto transactionDto) {
        return mapper.transactionToTransactionDto(repository.update(id,mapper.transactionDtoToTransaction(transactionDto)));

    }

    @Override
    public void deleteTransaction(int id) {
        repository.delete(id);
    }

    @Override
    public List<TransactionDto> findAll() {
        return repository.find(transaction -> true).stream().map(mapper::transactionToTransactionDto).collect(Collectors.toList());
    }
}
