package om.mindhub.homebanking.services.implement;

import om.mindhub.homebanking.dtos.AccountDTO;
import om.mindhub.homebanking.models.Account;
import om.mindhub.homebanking.repositories.AccountRepository;
import om.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {


    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public AccountDTO getAccount(long id) {
        return new AccountDTO(this.findById(id));
    }

    @Override
    public Account createAccount() {
        return new Account(this.createNumberAccount(), LocalDateTime.now(), 0.0);
    }

    @Override
    public boolean existsByNumber(String accountNumber) {
        return false;
    }

    @Override
    public String createNumberAccount() {
         String accountNumber;
        do {
            int random = (int) (Math.random() * 99999999);
            accountNumber = "VIN-" + random;
        } while (this.existsByNumber(accountNumber));
        return accountNumber;
    }
}
