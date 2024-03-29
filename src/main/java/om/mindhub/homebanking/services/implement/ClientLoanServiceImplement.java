package om.mindhub.homebanking.services.implement;

import om.mindhub.homebanking.dtos.ClientLoanDTO;
import om.mindhub.homebanking.models.ClientLoan;
import om.mindhub.homebanking.repositories.ClientLoanRepository;
import om.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public ClientLoan findById(long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public ClientLoanDTO getClientLoan(long id) {
        return new ClientLoanDTO(this.findById(id));
    }

    @Override
    public ClientLoan createClientLoan(double amount, int payments) {
        return new ClientLoan(amount, payments);
    }
}
