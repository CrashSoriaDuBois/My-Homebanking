package om.mindhub.homebanking.controllers;



import om.mindhub.homebanking.dtos.TransactionDTO;
import om.mindhub.homebanking.enums.TransactionType;
import om.mindhub.homebanking.models.Account;
import om.mindhub.homebanking.models.Transaction;
import om.mindhub.homebanking.repositories.AccountRepository;
import om.mindhub.homebanking.repositories.ClientRepository;
import om.mindhub.homebanking.repositories.TransactionRepository;
import om.mindhub.homebanking.services.AccountService;
import om.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createCurrentCard(@RequestParam double amount, @RequestParam String
            description, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication) {
        //validate client
     /*   boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }     */
        //get accounts
        Account sourceAccount = accountService.findByNumber(fromAccountNumber);
        Account destinationAccount= accountService.findByNumber(toAccountNumber);
        //validate parameters
        if (amount <= 0) {
            return new ResponseEntity<>("Amount invalid", HttpStatus.FORBIDDEN);
        }
        if ( description.isEmpty()) {
            return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Number of source account is empty", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Number of destinaton account is empty", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("destination account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(sourceAccount==null){
            return new ResponseEntity<>("Source account not found",HttpStatus.FORBIDDEN);
        }
        if(destinationAccount==null){
            return new ResponseEntity<>("Destination account not found",HttpStatus.FORBIDDEN);
        }
        //validate current account with source account
        if(!sourceAccount.getClient().getEmail().equals(authentication.getName())){
            return new ResponseEntity<>("Source account must be yours",HttpStatus.FORBIDDEN);
        }
        //verify has balance
        if(sourceAccount.getBalance()<amount){
            return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction= transactionService.createDebitTransaction(amount,description);
        Transaction creditTransaction= transactionService.createCreditTransaction(amount,description);
        sourceAccount.addTransactions(debitTransaction);
        destinationAccount.addTransactions(creditTransaction);
        //change balance
        sourceAccount.minusBalance(amount);
        destinationAccount.plusBalance(amount);
        //save transactions
        accountService.saveAccount(sourceAccount);
        accountService.saveAccount(destinationAccount);
        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
