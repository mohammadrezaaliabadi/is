package is.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer",indexes = {@Index(name = "firstName",columnList = "firstName"),@Index(name = "lastName",columnList = "lastName"),@Index(name = "nationalNumber",columnList = "nationalNumber")})
public class Customer implements Serializable {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String nationalNumber;
}
