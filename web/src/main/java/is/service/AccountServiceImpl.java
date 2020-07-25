package is.service;

import is.repository.AccountRepository;
import is.repository.CustomerRepository;
import is.web.mapper.AccountMapper;
import is.web.mapper.CustomerMapper;
import is.web.model.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountMapper mapper;
    @Override
    public AccountDto saveAccount(AccountDto accountDto) {
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
        return repository.find(account -> true).stream().map(mapper::accountToAccountDto).collect(Collectors.toList());
    }
}
