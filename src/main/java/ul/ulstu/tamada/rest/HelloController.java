package ul.ulstu.tamada.rest;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> getHello()
    {
        return new ResponseEntity<>("Hello from tamada da backend!", HttpStatus.OK);
    }
}
