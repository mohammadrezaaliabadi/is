package is.web.controller;

import is.domain.Card;
import is.service.AccountService;
import is.service.CardService;
import is.web.model.AccountDto;
import is.web.model.CardDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    private CardService service;
    @GetMapping("/{cardId}")
    public ResponseEntity<CardDto> get(@PathVariable("cardId")int cardId){
        return new ResponseEntity<>(service.findById(cardId), HttpStatus.OK);
    }
    @GetMapping(path = {"","/"})
    public ResponseEntity<List<CardDto>> getAll(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{cardNumber}/cardNumber")
    public ResponseEntity<CardDto> get(@PathVariable("accountNumber")String cardNumber){
        return new ResponseEntity<>(service.findCardNumber(cardNumber), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CardDto> post(@RequestBody CardDto cardDto) throws NotFoundException {
        return new ResponseEntity<>(service.saveCard(cardDto),HttpStatus.CREATED);
    }
    @PutMapping("/{cardId}")
    public ResponseEntity update(@PathVariable("cardId") int cardId,@RequestBody CardDto cardDto){
        return new ResponseEntity(service.updateCard(cardId,cardDto),HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cardId") int cardId){
        service.deleteCard(cardId);
    }
}
