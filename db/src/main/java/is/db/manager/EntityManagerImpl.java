package is.db.manager;

import is.db.meta.Table;
import is.db.rw.bytes.SeekByteRW;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class EntityManagerImpl<T extends Serializable, ID extends Comparable<? super ID>> implements EntityManager<T, ID>, Closeable {
    private SeekByteRW<T, ID> seekByteRW;
    private Table table;

    @Override
    public T findById(ID id) {
        return seekByteRW.findById(id);
    }

    @Override
    public T save(T t) {
        Field field = table.getKeys().get(0);
        field.setAccessible(true);
        try {
            ID id = (ID) field.get(t);
            if (seekByteRW.findById(id)!=null) {
                System.out.println("id is exist");
            } else {
                seekByteRW.save(t,field);
                return findById(id);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(ID id) {
        seekByteRW.delete(id);
    }

    @Override
    public T update(ID id, T t) {
        seekByteRW.delete(id);
        return save(t);
    }

    @Override
    public List<T> find(Predicate<T> predicate) {
        return seekByteRW.findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public void close() throws IOException {
        seekByteRW.close();
    }
}
