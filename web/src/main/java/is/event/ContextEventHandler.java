package is.event;

import is.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextEventHandler implements ApplicationListener<ContextClosedEvent>{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (accountRepository instanceof AccountRepositoryImpl){
            ((AccountRepositoryImpl) accountRepository).close();
        }
        if (cardRepository instanceof CardRepositoryImpl){
            ((CardRepositoryImpl) cardRepository).close();
        }
        if (customerRepository instanceof CustomerRepositoryImpl){
            ((CustomerRepositoryImpl) customerRepository).close();
        }

        if (transactionRepository instanceof TransactionRepositoryImpl){
            ((TransactionRepositoryImpl) transactionRepository).close();
        }
    }
}
