package is.service;


import is.domain.Account;
import is.web.model.AccountDto;
import is.web.model.CustomerDto;
import javassist.NotFoundException;

import java.util.List;

public interface AccountService {
    AccountDto saveAccount(AccountDto accountDto) throws NotFoundException;
    AccountDto findById(int id);
    AccountDto updateAccount(int id,AccountDto accountDto);
    void deleteAccount(int id);
    List<AccountDto> findAll();
    AccountDto findByAccountNumber(String accountNumber);
}
