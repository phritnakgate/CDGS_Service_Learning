package th.co.cdg.SimpleApplication.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import th.co.cdg.SimpleApplication.model.Customer;
import th.co.cdg.SimpleApplication.repository.CustomerRepository;

import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;


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

    @PostMapping(value = "/api/customer/user-and-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addUserAndImage(@RequestParam(name="userData") String userData,
                                                  @RequestParam(name="image") MultipartFile image) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(userData, Customer.class);
        byte[] userImg = image.getBytes();
        customer.setImage(userImg);
        try {
            int result = customerRepository.addCustomer(customer);
            if(result != 0){
                return ResponseEntity.ok("Customer with image added successfully!");
            }else{
                return ResponseEntity.badRequest().body("Please enter a valid customer info!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding customer with image! Check your request body.");
        }

    }

    @GetMapping(value = "/api/customer/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getCustomerImage(@PathVariable Long id) {
        try {
            byte[] img = customerRepository.queryCustomerImageById(id);
            if (img != null) {
                return ResponseEntity.ok(img);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

}
