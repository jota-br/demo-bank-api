package ostro.veda.bank.api.service;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.model.Account;
import ostro.veda.bank.api.model.AccountType;
import ostro.veda.bank.api.model.User;
import ostro.veda.bank.api.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {
        boolean userExists = userRepository.existsByName(userDto.getName());
        if (userExists) throw new EntityExistsException("User already exists");

        User user = getUser(userDto);
        user = userRepository.save(user);
        return user.toDto();
    }

    private User getUser(UserDto userDto) {
        return new User()
                .setName(userDto.getName())
                .setPassword(userDto.getPassword())
                .setAccounts(getAccounts());
    }

    private Set<Account> getAccounts() {
        return Set.of(
                new Account()
                        .setAccountType(AccountType.SAVINGS)
                        .setBalance(BigDecimal.valueOf(0.0d))
                        .setAccountNumber(getAccountNumber())
                        .setAvailableLimit(BigDecimal.valueOf(0.0d))
                        .setMaxLimit(BigDecimal.valueOf(0.0d)),
                new Account()
                        .setAccountType(AccountType.CHECKING)
                        .setBalance(BigDecimal.valueOf(0.0d))
                        .setAccountNumber(getAccountNumber())
                        .setAvailableLimit(BigDecimal.valueOf(1000.0d))
                        .setMaxLimit(BigDecimal.valueOf(1000.0d))
        );
    }

    private String getAccountNumber() {
        Random random = new Random();
        return System.currentTimeMillis() + "-" + String.format("%04d", random.nextInt(10000));
    }
}
