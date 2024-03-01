package om.mindhub.homebanking.dtos;

import om.mindhub.homebanking.models.Account;
import om.mindhub.homebanking.models.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private Long id;

    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private Set<TransactionDTO> transactions;
    public AccountDTO(Account account){
        this.id = account.getAccountID();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

}
