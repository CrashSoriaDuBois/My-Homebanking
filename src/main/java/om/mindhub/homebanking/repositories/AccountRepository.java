package om.mindhub.homebanking.repositories;

import om.mindhub.homebanking.models.Account;
import om.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByClientEmail(String email);

    boolean existsByNumber(String number);
    Account findByNumber(String number);
}
