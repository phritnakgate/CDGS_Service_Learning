package th.co.cdg.SimpleApplication.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import th.co.cdg.SimpleApplication.model.User;

@RestController
public class SimpleController {
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
}
