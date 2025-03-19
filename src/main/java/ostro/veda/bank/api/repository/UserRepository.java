package ostro.veda.bank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ostro.veda.bank.api.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);
    boolean existsByName(String name);
}
