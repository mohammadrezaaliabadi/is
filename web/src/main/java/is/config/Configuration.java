package is.config;

import com.fasterxml.jackson.core.type.TypeReference;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.MetaDB;
import is.db.rw.bytes.SeekByteRW;
import is.domain.Account;
import is.domain.Card;
import is.domain.Customer;
import is.domain.Transaction;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private Path path = Paths.get("data");

    @Bean
    public MetaDB metaDB() throws IOException {
        MetaDB metaDB = new MetaDB(path, "bank");
        metaDB.loadTableInJavaClass("is.domain");
        metaDB.writeMetaDb();
        return metaDB;
    }

    @Bean
    public EntityManager<Customer, Integer> entityManagerCustomer(MetaDB metaDB) throws IOException {
        SeekByteRW<Customer, Integer> seekByteRW = new SeekByteRW(path.resolve("bank/customer"), metaDB.getDb().getTables().get("customer"), Customer.class, new TypeReference<List<Customer>>() {
        });
        return new EntityManagerImpl<>(seekByteRW);
    }

    @Bean
    public EntityManager<Account, Integer> entityManagerAccount(MetaDB metaDB) throws IOException {
        SeekByteRW<Account, Integer> seekByteRW = new SeekByteRW(path.resolve("bank/account"), metaDB.getDb().getTables().get("account"), Account.class, new TypeReference<List<Account>>() {
        });
        return new EntityManagerImpl(seekByteRW);
    }

    @Bean
    public EntityManager<Card, Integer> entityManagerCard(MetaDB metaDB) throws IOException {
        SeekByteRW<Card, Integer> seekByteRW = new SeekByteRW(path.resolve("bank/card"), metaDB.getDb().getTables().get("card"), Card.class, new TypeReference<List<Card>>() {
        });
        return new EntityManagerImpl(seekByteRW);
    }

    @Bean
    public EntityManager<Transaction, Integer> entityManagerTransaction(MetaDB metaDB) throws IOException {
        SeekByteRW<Transaction, Integer> seekByteRW = new SeekByteRW(path.resolve("bank/transaction"), metaDB.getDb().getTables().get("transaction"), Transaction.class, new TypeReference<List<Transaction>>() {
        });
        return new EntityManagerImpl(seekByteRW);
    }
}
