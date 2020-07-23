package is.config;

import is.db.meta.MetaDB;
import is.db.model.Customer;
import is.db.repository.CustomerRepository;
import is.db.repository.CustomerRepositoryImpl;
import org.springframework.context.annotation.Bean;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private Path path = Paths.get("db/data");
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
    public Me me(){
        return new Me("==================");
    }

}
