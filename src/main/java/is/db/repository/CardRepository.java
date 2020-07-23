package is.db.repository;



import is.db.model.Card;

import java.util.List;
import java.util.function.Predicate;

public interface CardRepository {
    Card findById(Integer id);
    void save(Card t);
    void delete(Integer id);
    void update(Integer id,Card t);
    List<Card> find(Predicate<Card> predicate);
}
