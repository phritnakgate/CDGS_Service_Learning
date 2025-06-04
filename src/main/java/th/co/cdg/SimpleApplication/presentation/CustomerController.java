package th.co.cdg.SimpleApplication.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.co.cdg.SimpleApplication.model.Customer;
import th.co.cdg.SimpleApplication.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    //1. Get All Customers
    @GetMapping(value = "/api/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.queryAllCustomers();
        if(customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(customers);
        }
    }

    //2. Get user by id
    @GetMapping(value = "/api/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try{
            Customer customer = customerRepository.queryCustomerById(id);
            if(customer == null){
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.ok(customer);
            }
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }

    }

    //3. Add Customer
    @PostMapping(value = "/api/customer/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        try{
            int result = customerRepository.addCustomer(customer);
            if(result != 0){
                return ResponseEntity.ok("Customer added successfully!");
            }else{
                return ResponseEntity.badRequest().body("Please enter a valid customer info!");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error adding customer! Check your request body.");
        }
    }

    //4. Update Customer
    @PatchMapping(value = "/api/customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try{
            int result = customerRepository.updateCustomer(id, customer);
            if(result != 0){
                return ResponseEntity.ok("Customer updated successfully!");
            }else {
                return ResponseEntity.badRequest().body("Please enter a valid customer ID!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Please enter a valid request body! ");
        }
    }

    //5. Delete By id
    @DeleteMapping(value = "/api/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        int result = customerRepository.deleteCustomer(id);
        if(result != 0){
            return ResponseEntity.ok("Customer deleted successfully!");
        }else{
            return ResponseEntity.badRequest().body("Please enter a valid customer ID!");
        }
    }

}
