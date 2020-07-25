package is.db.manager;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public interface EntityManager<T extends Serializable, ID> {
    T findById(ID id);

    T save(T t);

    void delete(ID id);

    T update(ID id, T t);

    List<T> find(Predicate<T> predicate);
    List<T> findAll();
    T findByIndex(Object o,String indexName);
}
