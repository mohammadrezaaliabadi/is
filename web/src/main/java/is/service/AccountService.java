package is.service;


import is.web.model.AccountDto;
import is.web.model.CustomerDto;

import java.util.List;

public interface AccountService {
    AccountDto saveAccount(AccountDto accountDto);
    AccountDto findById(int id);
    AccountDto updateAccount(int id,AccountDto accountDto);
    void deleteAccount(int id);
    List<AccountDto> findAll();
}
