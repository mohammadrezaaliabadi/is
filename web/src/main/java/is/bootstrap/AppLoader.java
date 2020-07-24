package is.bootstrap;

import is.repository.AccountRepository;
import is.repository.CardRepository;
import is.repository.CustomerRepository;
import is.repository.TransactionRepository;
import is.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppLoader implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;



    @Override
    public void run(String... args) throws Exception {
        initCustomer();

    }

    public void initCustomer() {

    }
}
