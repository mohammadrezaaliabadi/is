package is.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TransactionDto {
    private int id;
    private String transactionNumber;
    private int transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalBalance;
    private int accountFrom;
    private int accountTo;
}
