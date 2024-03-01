package om.mindhub.homebanking;

import om.mindhub.homebanking.enums.CardColor;
import om.mindhub.homebanking.enums.CardType;
import om.mindhub.homebanking.models.*;
import om.mindhub.homebanking.enums.TransactionType;
import om.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.Authenticator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			Account account1 = new Account("VIN001", LocalDateTime.now(),5000 );
			Account account2 = new Account("VIN002", (LocalDateTime.now()).plusDays(1),7500 );

			client1.addAccounts(account1);
			client1.addAccounts(account2);
			clientRepository.save(client1);
			accountRepository.save(account1);
			accountRepository.save(account2);

			Client client2 = new Client("Marina", "Risotto", "inesrisotto@gmail.com", passwordEncoder.encode("melba"));
			Account account3 = new Account("VIN003", LocalDateTime.now(),2500 );
			Account account4 = new Account("VIN004", (LocalDateTime.now()).plusDays(1),1000 );

			Client client3 = new Client("Admin", "Admin", "admin@admin.com", passwordEncoder.encode("admin"));
			clientRepository.save(client3);

			client2.addAccounts(account3);
			client2.addAccounts(account4);
			clientRepository.save(client2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT,5000,"description", LocalDate.now());
			transactionRepository.save(transaction1);
			account1.addTransactions(transaction1);

			Transaction transaction2 = new Transaction(TransactionType.CREDIT,5000,"other description",LocalDate.now());
			transactionRepository.save(transaction2);
			account2.addTransactions(transaction2);



			//
			Loan loan1 = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotive", 300000, List.of(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			ClientLoan clientLoan1 = new ClientLoan(400000,loan1.getPayments().get(4));
			ClientLoan clientLoan2 = new ClientLoan(50000,loan2.getPayments().get(1));
			ClientLoan clientLoan3 = new ClientLoan(100000,loan2.getPayments().get(2));
			ClientLoan clientLoan4 = new ClientLoan(200000,loan3.getPayments().get(2));


			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan3);
			loan3.addClientLoan(clientLoan4);
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);


			client1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			clientRepository.save(client1);
			clientRepository.save(client2);


			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT, CardColor.GOLD,"2316-5416-3854-2184",970,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.CREDIT, CardColor.TITANIUM,"5423-8465-3214-5483",320,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
			Card card3 = new Card(client1.getFirstName() + " " + client1.getLastName(),CardType.DEBIT, CardColor.SILVER,"5456-1321-4541-8973",110,LocalDateTime.now(),LocalDateTime.now().plusYears(5));

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			clientRepository.save(client1);
			clientRepository.save(client2);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
	});
	}
}
