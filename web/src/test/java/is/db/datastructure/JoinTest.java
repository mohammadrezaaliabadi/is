package is.db.datastructure;

import is.domain.Account;
import is.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


class JoinTest {

    @Test
    void nestedLoopJoin() throws NoSuchFieldException, IllegalAccessException {
        Account[] accounts = new Account[2];
        accounts[0] = Account.builder().id(1).accountNumber("123123").balance(new BigDecimal("234234")).customerId(1).build();
        accounts[1] = Account.builder().id(2).accountNumber("31231").balance(new BigDecimal("124342")).customerId(2).build();

        Transaction[] transactions = new Transaction[3];
        transactions[0] = Transaction.builder().transactionNumber("123").id(1).totalBalance(new BigDecimal("423423")).transactionType(2).accountFrom(1).accountTo(2).build();
        transactions[1] = Transaction.builder().transactionNumber("34").id(2).totalBalance(new BigDecimal("23")).transactionType(2).accountFrom(1).accountTo(2).build();
        transactions[2] = Transaction.builder().transactionNumber("42").id(3).totalBalance(new BigDecimal("412")).transactionType(2).accountFrom(2).accountTo(1).build();

        List list = Join.<Account,Transaction>nestedLoopJoin(accounts, Account.class.getDeclaredField("id"), transactions, Transaction.class.getDeclaredField("accountFrom"));
        System.out.println(list);
        List list2 = Join.<Account,Transaction>nestedLoopJoin(accounts, "id", transactions, "accountFrom");
        System.out.println(list2);
    }
}