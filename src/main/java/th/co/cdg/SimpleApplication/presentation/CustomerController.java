package th.co.cdg.SimpleApplication.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.co.cdg.SimpleApplication.model.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>();

    private Customer findCustomerById(Long id) {
        return customers
                .stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElse(null);
    }

    //1. Get All Customers
    @GetMapping(value = "/api/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        if(customers.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            customers.sort(Comparator.comparing(Customer::getId));
            return ResponseEntity.ok(customers);
        }
    }

    //2. Get user by id
    @GetMapping(value = "/api/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        if(findCustomerById(id) == null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findCustomerById(id));
        }
    }

    //3. Add Customer
    @PostMapping(value = "/api/customer/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        customer.setId((long) (customers.size() + 1));
        if(customer.getName() == null || customer.getSurname() == null || customer.getAddress() == null || customer.getAge() == null || customer.getTel() == null){
            return ResponseEntity.badRequest().body("Please enter a valid customer info!");
        }else{
            customers.add(customer);
            return ResponseEntity.ok("Customer added successfully!");
        }
    }

    //4. Update Customer
    @PatchMapping(value = "/api/customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if(findCustomerById(id) == null){
            return ResponseEntity.badRequest().body("Please enter a valid customer ID!");
        }else{
            Customer selectedCustomer = findCustomerById(id);
            if (customer.getName() != null) {
                selectedCustomer.setName(customer.getName());
            }
            if (customer.getSurname() != null) {
                selectedCustomer.setSurname(customer.getSurname());
            }
            if (customer.getAddress() != null) {
                selectedCustomer.setAddress(customer.getAddress());
            }
            if (customer.getAge() != null) {
                selectedCustomer.setAge(customer.getAge());
            }
            if (customer.getTel() != null) {
                selectedCustomer.setTel(customer.getTel());
            }
            return ResponseEntity.ok().body("Customer updated successfully!");
        }
    }

    //5. Delete By id
    @DeleteMapping(value = "/api/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        if(findCustomerById(id) == null){
            return ResponseEntity.badRequest().body("Please enter a valid customer ID!");
        }else{
            customers.removeIf(c -> Objects.equals(c.getId(), id));
            return ResponseEntity.ok("Customer deleted successfully!");
        }
    }
    //6. Delete All records
    @DeleteMapping(value = "/api/customer/delete-all")
    public ResponseEntity<String> deleteAllCustomers() {
        customers.clear();
        return ResponseEntity.ok().body("All Customer Data deleted successfully!");
    }
}
