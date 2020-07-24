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


        SpringApplication.run(DemoApplication.class, args);
    }

}
