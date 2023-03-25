package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestConnectionController {

    @GetMapping(path = {"", "/"})
    public ResponseEntity<Void> testConnection() {
        return ResponseEntity.ok().build();
    }
}
