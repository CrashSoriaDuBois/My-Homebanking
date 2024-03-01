package om.mindhub.homebanking.services;

import om.mindhub.homebanking.dtos.CardDTO;
import om.mindhub.homebanking.enums.CardColor;
import om.mindhub.homebanking.enums.CardType;
import om.mindhub.homebanking.models.Card;
import om.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {
    List<CardDTO> getAllCards();
    void saveCard(Card card);
    Card findById(long id);
    boolean existsByTypeAndColorAndClient( CardType cardType,CardColor cardColor,Client client);
    Card createCard(String cardHolder, CardType cardType, CardColor cardColor);
    int generateCvv();
    String generateNumber();
}
