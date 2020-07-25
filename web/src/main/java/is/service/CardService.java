package is.service;

import is.web.model.AccountDto;
import is.web.model.CardDto;

import java.util.List;

public interface CardService {
    CardDto saveCard(CardDto cardDto);
    CardDto findById(int id);
    CardDto updateCard(int id,CardDto cardDto);
    void deleteCard(int id);
    List<CardDto> findAll();
}
