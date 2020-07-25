package is.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import is.db.annotation.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="account",indexes = {@Index(name = "accountNumber",columnList = "accountNumber")})
public class Account implements Serializable {
    @Id
    private int id;
    private String accountNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;
    @ForeignKey(name = "customerId", tableName = "customer")
    private int customerId;
}
