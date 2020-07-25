package is.web.controller;

import is.service.CardService;
import is.service.TransactionService;
import is.web.model.CardDto;
import is.web.model.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService service;
    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> get(@PathVariable("transactionId")int transactionId){
        return new ResponseEntity<>(service.findById(transactionId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TransactionDto> post(@RequestBody TransactionDto transactionDto){
        return new ResponseEntity<>(service.saveTransaction(transactionDto),HttpStatus.CREATED);
    }
    @PutMapping("/{transactionId}")
    public ResponseEntity update(@PathVariable("transactionId") int transactionId,@RequestBody TransactionDto transactionDto){
        return new ResponseEntity(service.updateTransaction(transactionId,transactionDto),HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("transactionId") int transactionId){
        service.deleteTransaction(transactionId);
        // todo impl
    }
}
