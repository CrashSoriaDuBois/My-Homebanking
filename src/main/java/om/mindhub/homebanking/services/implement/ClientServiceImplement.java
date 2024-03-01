package om.mindhub.homebanking.services.implement;

import om.mindhub.homebanking.dtos.ClienDTO;
import om.mindhub.homebanking.models.Client;
import om.mindhub.homebanking.repositories.ClientRepository;
import om.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClienDTO> getClients() {
        return clientRepository.findAll().stream().map(ClienDTO::new).collect(toList());
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClienDTO getClient(long id) {
        return new ClienDTO(this.findById(id));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClienDTO getClientCurrent(String email){
        return new ClienDTO(this.findByEmail(email));
    }

}
