package om.mindhub.homebanking.controllers;

import om.mindhub.homebanking.dtos.CardDTO;
import om.mindhub.homebanking.enums.CardColor;
import om.mindhub.homebanking.enums.CardType;
import om.mindhub.homebanking.models.Card;
import om.mindhub.homebanking.models.Client;
import om.mindhub.homebanking.repositories.CardRepository;
import om.mindhub.homebanking.repositories.ClientRepository;
import om.mindhub.homebanking.services.CardService;
import om.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<CardDTO> getAllCards(){
        return cardService.getAllCards();
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication) {
        Client currentClient= clientService.findByEmail(authentication.getName());
        return currentClient.getCards().stream().map(CardDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCurrentCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication){
        //validate
        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if(!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Client client = clientService.findByEmail(authentication.getName());

        if (cardService.existsByTypeAndColorAndClient(cardType, cardColor, client)) {
            return new ResponseEntity<>("card alredy exist", HttpStatus.FORBIDDEN);
        }

        Card newCard = cardService.createCard(client.cardHolder(),cardType,cardColor);
        client.addCard(newCard);
        cardService.saveCard(newCard);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
