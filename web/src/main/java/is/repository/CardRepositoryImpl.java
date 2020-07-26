package is.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
@Repository
public class CardRepositoryImpl implements CardRepository, Closeable {
    @Autowired
    private EntityManager<Card, Integer> entityManager;

    @Override
    public Card findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public Card save(Card t) {
        return entityManager.save(t);
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public Card update(Integer id, Card t) {
        return entityManager.update(id, t);
    }

    @Override
    public List<Card> find(Predicate<Card> predicate) {
        return entityManager.find(predicate);
    }

    @Override
    public List<Card> findAll() {
        return entityManager.findAllList();
    }

    @Override
    public Card findCardNumber(String cardNumber) {
        return entityManager.findByIndex(cardNumber,"cardNumber");
    }

    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Card, Integer>) entityManager).close();
        }
    }
}
