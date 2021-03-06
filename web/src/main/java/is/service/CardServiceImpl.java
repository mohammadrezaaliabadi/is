package is.service;

import is.domain.Account;
import is.domain.Card;
import is.domain.Customer;
import is.repository.AccountRepository;
import is.repository.CardRepository;
import is.web.mapper.CardMapper;
import is.web.model.CardDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private CardMapper mapper;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public CardDto saveCard(CardDto cardDto) throws NotFoundException {
        Account byId = accountRepository.findById(cardDto.getAccountId());
        if (byId==null){
            throw new NotFoundException("Customer Not Found for FK");
        }
        return mapper.cardToCardDto(repository.save(mapper.cardDtoToCard(cardDto)));
    }

    @Override
    public CardDto findById(int id) {
        return mapper.cardToCardDto(repository.findById(id));
    }

    @Override
    public CardDto updateCard(int id, CardDto cardDto) {
        return mapper.cardToCardDto(repository.update(id,mapper.cardDtoToCard(cardDto)));
    }

    @Override
    public void deleteCard(int id) {
        repository.delete(id);
    }

    @Override
    public List<CardDto> findAll() {
        return repository.findAll().stream().map(mapper::cardToCardDto).collect(Collectors.toList());
    }

    @Override
    public CardDto findCardNumber(String cardNumber) {
        return mapper.cardToCardDto(repository.findCardNumber(cardNumber));
    }
}
