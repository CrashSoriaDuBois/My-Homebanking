package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.AccountDTO;
import om.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();
    void saveAccount(Account account);
    Account findById(long id);
    Account findByNumber(String number);
    AccountDTO getAccount(long id);
    Account createAccount();

    boolean existsByNumber(String accountNumber);
    String createNumberAccount();


}
