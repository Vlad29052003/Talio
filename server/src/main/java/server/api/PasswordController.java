package server.api;

import commons.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.PasswordRepository;

import java.util.List;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordRepository repo;

    /**
     * Instantiate a new {@link PasswordController}.
     * @param repo the {@link PasswordRepository} to use.
     */
    @Autowired
    public PasswordController(PasswordRepository repo){
        Password pass = new Password();
        System.out.println("\n\nAdmin Password: " + pass.getPassword() + "\n\n");
        repo.deleteAll();
        repo.save(pass);
        this.repo = repo;
    }

    /**
     * Retrieves all passwords from the repository.
     *
     * @return All passwords.
     */
    @GetMapping(path = { "", "/" })
    public List<Password> getAll() {
        return repo.findAll();
    }
}
