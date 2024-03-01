package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.ClientLoanDTO;
import om.mindhub.homebanking.models.ClientLoan;

import java.util.List;


public interface ClientLoanService {
    List<ClientLoanDTO> getClientLoans();
    ClientLoan findById(long id);
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoanDTO getClientLoan(long id);
    ClientLoan createClientLoan(double amount,int payments);
}
