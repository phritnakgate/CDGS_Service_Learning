package th.co.cdg.SimpleApplication.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import th.co.cdg.SimpleApplication.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
public class SimpleController {

    // Hello World Endpoints \\
    @GetMapping(value="hello-with-name/{name}")
    public ResponseEntity<String> getHelloWorld(@PathVariable(name="name") String name) {
        try{
            return ResponseEntity.ok().body("Hello "+name+"!");
        }catch(HttpClientErrorException clientErrorException){
            if(clientErrorException.getStatusCode() == HttpStatus.BAD_REQUEST){
                return ResponseEntity.badRequest().body("Please provide a valid name.");
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping(value="hello-with-body", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postHelloWorld(@RequestBody User user) {
        return ResponseEntity.ok().body("Helo "+ user.getName() + "!");
    }

    // User Endpoints \\
    private final List<User> users = new ArrayList<>();

    private boolean isUserExist(int id){
        return users.stream().anyMatch(user -> user.getId() == id);
    }

    private User findUserById(int id) {
        return users
                .stream()
                .filter(user -> user.getId() == id)
                .findAny()
                .orElse(null);
    }

    @GetMapping(value="/api/users")
    public ResponseEntity<List<User>> getUsers() {
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            users.sort(Comparator.comparing(User::getId)); //Sort Data Based on ID
            return ResponseEntity.ok().body(users);
        }

    }

    @PostMapping(value="/api/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
       //Validation
        if(user.getId() <= 0 || user.getName() == null || user.getTel() == null){
            return ResponseEntity.badRequest().body("Invalid user data provided.");
        }
        if (isUserExist(user.getId())) {
            return ResponseEntity.badRequest().body("User with ID " + user.getId() + " already exists.");
        }
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with ID: " + user.getId());
    }
    @GetMapping(value="/api/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        if(isUserExist(id)){
            return ResponseEntity.ok().body(findUserById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value="/api/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        if(isUserExist(id)){
            User user = findUserById(id);
            users.remove(user);
            return ResponseEntity.ok().body("User with ID " + id + " deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value="/api/user/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable int id, @RequestBody User user) {
        if(null == user){
            return ResponseEntity.badRequest().body("Invalid user data provided.");
        }
        if(isUserExist(id)){
            User foundUser = findUserById(id);
            if(null != user.getName()){
                foundUser.setName(user.getName());
                return ResponseEntity.ok().body("User with ID " + id + " updated successfully.");
            }
            if(null != user.getTel()){
                foundUser.setTel(user.getTel());
            }

        }
        return ResponseEntity.notFound().build();
    }

}
