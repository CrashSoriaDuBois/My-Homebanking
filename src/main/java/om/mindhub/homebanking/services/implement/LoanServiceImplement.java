package om.mindhub.homebanking.services.implement;

import om.mindhub.homebanking.dtos.LoanDTO;
import om.mindhub.homebanking.models.Loan;
import om.mindhub.homebanking.repositories.LoanRepository;
import om.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoans(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public LoanDTO getLoan(long id) {
        return new LoanDTO(this.findById(id));
    }
}
