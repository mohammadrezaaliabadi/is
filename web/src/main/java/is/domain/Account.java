package is.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import is.db.annotation.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
public class Account implements Serializable {
    @Id
    private int id;
    private String accountNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;
    @ForeignKey(name = "customerId", tableName = "customer")
    private int customerId;
}
