package is.web.controller;

import is.domain.Account;
import is.service.AccountService;
import is.service.CustomerService;
import is.web.model.AccountDto;
import is.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService service;
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> get(@PathVariable("accountId")int accountId){
        return new ResponseEntity<>(service.findById(accountId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AccountDto> post(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(service.saveAccount(accountDto),HttpStatus.CREATED);
    }
    @PutMapping("/{accountId}")
    public ResponseEntity update(@PathVariable("accountId") int accountId,@RequestBody AccountDto accountDto){
        return new ResponseEntity(service.updateAccount(accountId,accountDto),HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("accountId") int accountId){
        service.deleteAccount(accountId);
        // todo impl
    }
}
