package is.db.meta;

import lombok.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Table implements Serializable {
    private String name;
    private Field[] fields;
    private List<String> nameBlocks;
    private List<Field> keys;
}