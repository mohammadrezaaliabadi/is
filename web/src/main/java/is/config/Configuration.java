package is.config;

import is.db.meta.MetaDB;
import is.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public CustomerRepository customerRepository(MetaDB metaDB) {
        return new CustomerRepositoryImpl(path.resolve("bank/customer"), metaDB.getDb().getTables().get("customer"));
    }

    @Bean
    public AccountRepository accountRepository(MetaDB metaDB) {
        return new AccountRepositoryImpl(path.resolve("bank/account"), metaDB.getDb().getTables().get("account"));
    }

    @Bean
    public TransactionRepository transactionRepository(MetaDB metaDB) {
        return new TransactionRepositoryImpl(path.resolve("bank/transaction"), metaDB.getDb().getTables().get("transaction"));
    }

    @Bean
    public CardRepository cardRepository(MetaDB metaDB) {
        return new CardRepositoryImpl(path.resolve("bank/card"), metaDB.getDb().getTables().get("card"));
    }

//    @EventListener(classes = {ContextClosedEvent.class, ContextStoppedEvent.class })
//    public void handleCloseAndStopContextEvents() {
//        System.out.println("Multi-event listener invoked");
//    }

}
