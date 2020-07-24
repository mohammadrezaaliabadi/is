package is.bootstrap;

import is.db.model.Customer;
import is.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppLoader implements CommandLineRunner {
    @Autowired
    CustomerService customerService;
    @Override
    public void run(String... args) throws Exception {
        initCustomer();
    }
    public void initCustomer(){
        Customer byId = customerService.findById(1);
        System.out.println(byId);
    }
}
