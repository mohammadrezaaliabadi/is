package is.web.controller;

import is.service.CustomerService;
import is.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId")int customerId){
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }

    @GetMapping(path = {"","/"})
    public ResponseEntity<List<CustomerDto>> getCustomer(){
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{firstName}/firstName")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("firstName")String firstName){
        return new ResponseEntity<>(customerService.findByFirstName(firstName), HttpStatus.OK);
    }
    @GetMapping("/{lastName}/lastName")
    public ResponseEntity<CustomerDto> getCustomer2(@PathVariable("firstName")String lastName){
        return new ResponseEntity<>(customerService.findByFirstName(lastName), HttpStatus.OK);
    }
    @GetMapping("/{nationalNumber}/nationalNumber")
    public ResponseEntity<CustomerDto> getCustomer3(@PathVariable("nationalNumber")String nationalNumber){
        return new ResponseEntity<>(customerService.findByNationalNumber(nationalNumber), HttpStatus.OK);
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
