package is.db.model;

import is.db.annotation.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transaction")
public class Transaction implements Serializable {
    private String transactionNumber;
    private int transactionType;
    private BigDecimal totalBalance;
    @ForeignKey(name = "accountFrom", tableName = "account")
    private int accountFrom;
    @ForeignKey(name = "accountTo", tableName = "account")
    private int accountTo;
}
