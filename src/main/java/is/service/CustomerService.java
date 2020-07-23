package is.service;

import is.config.Me;
import is.db.model.Customer;
import is.db.repository.CustomerRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private Me me;
    public String ge(){
        return me.getMessage();
    }
}
