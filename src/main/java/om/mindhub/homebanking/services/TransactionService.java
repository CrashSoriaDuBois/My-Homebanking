package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.TransactionDTO;
import om.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    void saveTransaction(Transaction transaction);
    Transaction findById(long id);
    TransactionDTO getTransaction(long id);

    Transaction createDebitTransaction(double amount, String description);
    Transaction createCreditTransaction(double amount, String description);
}
