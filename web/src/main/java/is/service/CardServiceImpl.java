package is.service;

import is.repository.CardRepository;
import is.web.mapper.CardMapper;
import is.web.model.CardDto;
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
    @Override
    public CardDto saveCard(CardDto cardDto) {
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
}
