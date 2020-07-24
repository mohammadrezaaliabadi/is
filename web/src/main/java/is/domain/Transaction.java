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
@Entity(name = "transaction")
public class Transaction implements Serializable {
    @Id
    private int id;
    private String transactionNumber;
    private int transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalBalance;
    @ForeignKey(name = "accountFrom", tableName = "account")
    private int accountFrom;
    @ForeignKey(name = "accountTo", tableName = "account")
    private int accountTo;
}
