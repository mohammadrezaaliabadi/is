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
public class EntityManagerImpl<T extends Serializable, ID> implements EntityManager<T, ID>, Closeable {
    private SeekByteRW<T, ID> seekByteRW;
    private Table table;

    @Override
    public T findById(ID id) {
        return seekByteRW.find(seekByteRW.findLoc(id, table.getKeys().get(0)));
    }

    @Override
    public void save(T t) {
        Field field = table.getKeys().get(0);
        field.setAccessible(true);
        try {
            System.out.println(seekByteRW.findLoc((ID) field.get(t), field));
            if (seekByteRW.findLoc((ID) field.get(t), field) != -1) {
            } else {
                seekByteRW.save(t);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ID id) {
        seekByteRW.delete(seekByteRW.findLoc(id, table.getKeys().get(0)));
    }

    @Override
    public void update(ID id, T t) {
        seekByteRW.delete(seekByteRW.findLoc(id, table.getKeys().get(0)));
        save(t);
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
