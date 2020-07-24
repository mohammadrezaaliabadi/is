package is.web.controller;

import is.domain.Customer;
import is.service.CustomerService;
import is.web.model.CustomerDto;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId")int customerId){
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CustomerDto> postCustomer(@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.saveCustomer(customerDto),HttpStatus.CREATED);
    }
    @PutMapping("/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") int customerId,@RequestBody CustomerDto customerDto){
        return new ResponseEntity(customerService.updateCustomer(customerId,customerDto),HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") int customerId){
        customerService.deleteCustomer(customerId);
        // todo impl
    }


}
