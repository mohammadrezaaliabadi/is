package is.repository;


import is.domain.Card;

import java.util.List;
import java.util.function.Predicate;

public interface CardRepository {
    Card findById(Integer id);

    Card save(Card t);

    void delete(Integer id);

    Card update(Integer id, Card t);

    List<Card> find(Predicate<Card> predicate);
    List<Card> findAll();
    Card findCardNumber(String cardNumber);
}
