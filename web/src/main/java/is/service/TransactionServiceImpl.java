package is.service;

import is.repository.AccountRepository;
import is.repository.CustomerRepository;
import is.repository.TransactionRepository;
import is.web.mapper.AccountMapper;
import is.web.mapper.TransactionMapper;
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
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;


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
        return repository.findAll().stream().map(mapper::transactionToTransactionDto).collect(Collectors.toList());
    }
}
