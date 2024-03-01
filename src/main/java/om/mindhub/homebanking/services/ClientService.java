package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.ClienDTO;
import om.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<ClienDTO> getClients();
    void saveClient(Client client);
    Client findById(long id);
    ClienDTO getClient(long id);

    Client findByEmail(String email);

    ClienDTO getClientCurrent(String email);

}
