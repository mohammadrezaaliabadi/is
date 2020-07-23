package is.db.meta;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Field;

@Setter
@Getter
@ToString
@SuperBuilder
public class Key {
    private Field field;
    private KeyType keyType;
    private String nameTable;
}
