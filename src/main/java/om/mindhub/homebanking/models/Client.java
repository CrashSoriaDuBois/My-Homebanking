package om.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id; //
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();
    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccounts(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }


    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.setClient(this);
        cards.add(card);
    }
    @JsonIgnore
    public List<Loan> getLoans(){
        return clientLoans.stream().map(ClientLoan::getLoan).collect(toList());
    }
    public String cardHolder(){
        return firstName + " " + lastName;
    }
}
