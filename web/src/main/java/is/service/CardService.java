package is.service;

import is.domain.Card;
import is.web.model.AccountDto;
import is.web.model.CardDto;
import javassist.NotFoundException;

import java.util.List;

public interface CardService {
    CardDto saveCard(CardDto cardDto) throws NotFoundException;
    CardDto findById(int id);
    CardDto updateCard(int id,CardDto cardDto);
    void deleteCard(int id);
    List<CardDto> findAll();
    CardDto findCardNumber(String cardNumber);
}
