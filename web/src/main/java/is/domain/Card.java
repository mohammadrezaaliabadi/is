package is.domain;

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
@Entity(name = "card")
public class Card implements Serializable {
    @Id
    private int id;
    private String cardNumber;
    private int ccv;
    private String password;
    private int validityTime;
    @ForeignKey(name = "accountId", tableName = "account")
    private int accountId;


}