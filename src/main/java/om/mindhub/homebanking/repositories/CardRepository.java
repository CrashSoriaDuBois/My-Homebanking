package om.mindhub.homebanking.repositories;

import om.mindhub.homebanking.enums.CardColor;
import om.mindhub.homebanking.enums.CardType;
import om.mindhub.homebanking.models.Card;
import om.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByTypeAndColorAndClient (CardType type, CardColor color, Client client);
}
