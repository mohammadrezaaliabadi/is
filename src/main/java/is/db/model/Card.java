package is.db.model;

import is.db.annotation.ForeignKey;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "card")
public class Card implements Serializable {
    @Id
    private String cardNumber;
    private int ccv;
    private String password;
    private int validityTime;
    @ForeignKey(name = "accountId",tableName = "account")
    private int accountId;


}
