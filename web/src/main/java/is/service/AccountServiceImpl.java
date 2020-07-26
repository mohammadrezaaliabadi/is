package is.service;

import is.db.datastructure.MergeSort;
import is.domain.Account;
import is.domain.Customer;
import is.repository.AccountRepository;
import is.repository.CustomerRepository;
import is.web.mapper.AccountMapper;
import is.web.mapper.CustomerMapper;
import is.web.model.AccountDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountMapper mapper;
    @Override
    public AccountDto saveAccount(AccountDto accountDto) throws NotFoundException {
        Customer byId = customerRepository.findById(accountDto.getCustomerId());
        if (byId==null){
            throw new NotFoundException("Customer Not Found for FK");
        }
        return mapper.accountToAccountDto(repository.save(mapper.accountDtoToAccount(accountDto)));
    }

    @Override
    public AccountDto findById(int id) {
        return mapper.accountToAccountDto(repository.findById(id));
    }

    @Override
    public AccountDto updateAccount(int id, AccountDto accountDto) {
        return mapper.accountToAccountDto(repository.update(id,mapper.accountDtoToAccount(accountDto)));
    }

    @Override
    public void deleteAccount(int id) {
        repository.delete(id);
    }
    @Override
    public List<AccountDto> findAll() {
        return new MergeSort<Account>(repository.findAll()).getSortListItem().stream().map(mapper::accountToAccountDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) {
        return mapper.accountToAccountDto(repository.findByAccountNumber(accountNumber));
    }
}
