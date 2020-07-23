package is;

import com.fasterxml.jackson.core.type.TypeReference;
import is.config.Me;
import is.db.manager.EntityManager;
import is.db.manager.EntityManagerImpl;
import is.db.meta.MetaDB;
import is.db.model.Customer;
import is.db.rw.bytes.SeekByteRW;
import is.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IllegalAccessException, IOException {
//        Path path = Paths.get("db/data");
//        MetaDB metaDB = new MetaDB(path,"bank");
//        metaDB.loadTableInJavaClass("is.db.model");
//
//        SeekByteRW<Customer,Integer> seekByteRW = new SeekByteRW(path.resolve("bank/customer"), Customer.class, new TypeReference<List<Customer>>(){});
//        EntityManager<Customer,Integer> entityManager = new EntityManagerImpl(seekByteRW,metaDB.getDb().getTables().get("customer"));
//        entityManager.save(Customer.builder().id(2).firstName("ali").lastName("ahmadi").build());
//        seekByteRW.writeHeader();
        //customerService.save(Customer.builder().id(3).firstName("ali").lastName("asd").build());


        SpringApplication.run(DemoApplication.class, args);
        CustomerService customerService = new CustomerService();
        String ge = customerService.ge();
        System.out.println(ge);
    }

}
