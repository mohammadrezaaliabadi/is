package is.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.Table;
import is.db.model.Card;
import is.db.rw.bytes.SeekByteRW;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class CardRepositoryImpl implements CardRepository, Closeable {
    private EntityManager<Card, Integer> entityManager;

    public CardRepositoryImpl(Path path, Table table) {
        try {
            SeekByteRW<Card, Integer> seekByteRW = new SeekByteRW(path, Card.class, new TypeReference<List<Card>>() {
            });
            this.entityManager = new EntityManagerImpl<>(seekByteRW, table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card findById(Integer id) {
        return entityManager.findById(id);
    }

    @Override
    public void save(Card t) {
        try {
            entityManager.save(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        entityManager.delete(id);
    }

    @Override
    public void update(Integer id, Card t) {
        entityManager.update(id, t);
    }

    @Override
    public List<Card> find(Predicate<Card> predicate) {
        return entityManager.find(predicate);
    }
    @Override
    public void close() throws IOException {
        if (entityManager instanceof EntityManagerImpl){
            ((EntityManagerImpl<Card, Integer>) entityManager).close();
        }
    }
}
