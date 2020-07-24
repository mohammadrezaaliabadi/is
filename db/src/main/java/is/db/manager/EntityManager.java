package is.db.manager;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public interface EntityManager<T extends Serializable, ID> {
    T findById(ID id);

    void save(T t) throws IllegalAccessException;

    void delete(ID id);

    void update(ID id, T t);

    List<T> find(Predicate<T> predicate);
}
