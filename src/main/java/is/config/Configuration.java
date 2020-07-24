package is.config;

import com.is.repository.CardRepositoryImpl;
import is.db.meta.MetaDB;
import is.db.repository.*;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private Path path = Paths.get("data");
    @Bean
    public MetaDB metaDB(){
        MetaDB metaDB = new MetaDB(path,"bank");
        metaDB.loadTableInJavaClass("is.db.model");
        return metaDB;
    }
    @Bean
    public CustomerRepository customerRepository(MetaDB metaDB){
        return new CustomerRepositoryImpl(path.resolve("bank/customer"),metaDB.getDb().getTables().get("customer"));
    }
    @Bean
    public AccountRepository accountRepository(MetaDB metaDB){
        return new AccountRepositoryImpl(path.resolve("bank/account"),metaDB.getDb().getTables().get("account"));
    }

    @Bean
    public TransactionRepository transactionRepository(MetaDB metaDB){
        return new TransactionRepositoryImpl(path.resolve("bank/transaction"),metaDB.getDb().getTables().get("transaction"));
    }
    @Bean
    public CardRepository cardRepository(MetaDB metaDB){
        return new CardRepositoryImpl(path.resolve("bank/card"),metaDB.getDb().getTables().get("card"));
    }

}