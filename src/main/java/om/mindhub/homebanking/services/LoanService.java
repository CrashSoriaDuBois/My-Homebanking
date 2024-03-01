package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.LoanDTO;
import om.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    Loan findById(long id);
    void saveLoans(Loan loan);
    LoanDTO getLoan(long id);

}
