package om.mindhub.homebanking.controllers;
import om.mindhub.homebanking.dtos.ClienDTO;
import om.mindhub.homebanking.models.Account;
import om.mindhub.homebanking.models.Client;
import om.mindhub.homebanking.repositories.AccountRepository;
import om.mindhub.homebanking.repositories.ClientRepository;
import om.mindhub.homebanking.services.AccountService;
import om.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClienDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClienDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }
    @GetMapping("/clients/current")
    public ClienDTO getClientCurrent(Authentication authentication){
        return clientService.getClientCurrent(authentication.getName());
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Account account = accountService.createAccount();
        //save account
        accountService.saveAccount(account);
        //create new client
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        client.addAccount(account);
        //save client
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
