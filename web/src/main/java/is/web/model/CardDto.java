package is.web.model;

import is.db.annotation.ForeignKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto{
    private int id;
    private String cardNumber;
    private int ccv;
    private String password;
    private int validityTime;
    private int accountId;

}
