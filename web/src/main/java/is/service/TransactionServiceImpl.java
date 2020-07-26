package is.service;

import is.db.datastructure.Join;
import is.domain.Account;
import is.domain.Transaction;
import is.repository.AccountRepository;
import is.repository.CustomerRepository;
import is.repository.TransactionRepository;
import is.web.mapper.AccountMapper;
import is.web.mapper.TransactionMapper;
import is.web.model.TransactionDto;
import javassist.NotFoundException;
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


    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) throws NotFoundException {
        Account byId = accountRepository.findById(transactionDto.getAccountFrom());
        Account byId2 = accountRepository.findById(transactionDto.getAccountTo());
        if (byId ==null || byId2==null){
            throw new NotFoundException("Account not for Fk");
        }

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
        return List.of(repository.findAll()).stream().map(mapper::transactionToTransactionDto).collect(Collectors.toList());
    }

    @Override
    public List fromAccountTransaction() throws NoSuchFieldException, IllegalAccessException {
        return Join.nestedLoopJoin(accountRepository.findAll(),Account.class.getDeclaredField("id"),repository.findAll(), Transaction.class.getDeclaredField("accountFrom"));
    }

    @Override
    public List toAccountTransaction() throws NoSuchFieldException, IllegalAccessException {
        return Join.nestedLoopJoin(accountRepository.findAll(),Account.class.getDeclaredField("id"),repository.findAll(), Transaction.class.getDeclaredField("accountTo"));
    }
}
