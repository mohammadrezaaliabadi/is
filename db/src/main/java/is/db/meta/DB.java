package is.db.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DB implements Serializable {
    private String name;
    private List<String> nameTables;
    //@JsonIgnore
    private Map<String, Table> tables;
}
