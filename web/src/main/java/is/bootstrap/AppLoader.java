package is.bootstrap;

import is.db.model.Customer;
import is.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppLoader implements CommandLineRunner {
    @Autowired
    CustomerService customerService;

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        initCustomer();

    }

    public void initCustomer() {
        customerService.save(Customer.builder().id(1).firstName("ali").lastName("ahmad").build());
        Customer byId = customerService.findById(1);
        System.out.println(byId);
        SpringApplication.exit(context,()->0);
    }
}
