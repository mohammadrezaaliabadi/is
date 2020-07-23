package is.db.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
public class Customer implements Serializable {
    @Id
    private int id;
    private String firstName;
    private String lastName;
}
