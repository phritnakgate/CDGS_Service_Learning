package th.co.cdg.SimpleApplication.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @GetMapping(value="hello-world")
    public ResponseEntity<String> getHelloWorld(@RequestParam(name="name", defaultValue = "World") String name) {
        return ResponseEntity.ok().body("Hello "+name+"!");
    }
}
