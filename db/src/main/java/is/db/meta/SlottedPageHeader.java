package is.db.meta;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class SlottedPageHeader implements Serializable {
    private int numberOfRecord;
    private long endFreeSpace;
    private List<Integer> sizes;
    private List<Long> locations;
}
