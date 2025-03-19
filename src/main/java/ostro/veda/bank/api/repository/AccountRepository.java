package ostro.veda.bank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ostro.veda.bank.api.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByAccountNumber(String accountNumber);
}
