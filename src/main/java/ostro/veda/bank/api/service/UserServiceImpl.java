package ostro.veda.bank.api.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.dto.UserAuthDto;
import ostro.veda.bank.api.dto.UserSession;
import ostro.veda.bank.api.model.Account;
import ostro.veda.bank.api.model.AccountType;
import ostro.veda.bank.api.model.User;
import ostro.veda.bank.api.repository.UserRepository;
import ostro.veda.bank.api.security.JWTCreator;
import ostro.veda.bank.api.security.JWTObject;
import ostro.veda.bank.api.security.PasswordEncoder;
import ostro.veda.bank.api.security.SecurityConfig;

import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserInformation(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username %s not found".formatted(username)));
        return user.toDto();
    }

    @Override
    public UserDto createNewUser(UserAuthDto userAuthDto) throws NoSuchAlgorithmException {
        boolean userExists = userRepository.existsByUsername(userAuthDto.getName());
        if (userExists) throw new EntityExistsException("User already exists");

        if (userAuthDto.getName().isBlank()) throw new IllegalStateException("Name field is required");
        else if (userAuthDto.getPassword().isBlank()) throw new IllegalStateException("Password field is required");
        else if (userAuthDto.getPassword().length() < 8) throw new IllegalStateException("Password field must contain at least 8 characters");

        User user = getUser(userAuthDto);
        user = userRepository.save(user);
        return user.toDto();
    }

    public UserSession login(UserAuthDto userAuthDto) throws InvalidKeyException, NoSuchAlgorithmException {
        User user = userRepository.findByUsername(userAuthDto.getName())
                .orElseThrow(() -> new EntityNotFoundException("User with username %s not found"
                        .formatted(userAuthDto.getName())));
        if (passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword(), user.getSalt())) {
            UserSession userSession = new UserSession();
            userSession.setUser(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(user.getUsername());
            jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(List.of("USERS"));
            userSession.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return userSession;
        }
        throw new InvalidKeyException("Password doesn't match");
    }

    private User getUser(UserAuthDto userAuthDto) throws NoSuchAlgorithmException {
        String salt = passwordEncoder.getEncodedSalt();
        String encodedPassword = passwordEncoder.encode(userAuthDto.getPassword(), salt);
        return new User()
                .setUsername(userAuthDto.getName())
                .setPassword(encodedPassword)
                .setSalt(salt)
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
