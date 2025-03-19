package ostro.veda.bank.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.TransactionDto;
import ostro.veda.bank.api.dto.TransactionType;
import ostro.veda.bank.api.model.Account;
import ostro.veda.bank.api.model.User;
import ostro.veda.bank.api.repository.AccountRepository;
import ostro.veda.bank.api.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto doTransaction(TransactionDto transactionDto) {

        Account account = accountRepository.findByAccountNumber(transactionDto.getAccountNumber())
                .orElseThrow(() ->
                        new EntityNotFoundException("Account with number %s not found"
                                .formatted(transactionDto.getAccountNumber())));

        BigDecimal totalAvailable = account.getBalance().add(account.getAvailableLimit());

        if (transactionDto.getTransactionType().equals(TransactionType.WITHDRAWAL)) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            User user = userRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("User not %s not found".formatted(name)));

            if (user.getAccounts()
                    .stream()
                    .noneMatch(a -> a.getAccountNumber().equals(transactionDto.getAccountNumber())))
                throw new AuthorizationDeniedException("User not authorized to withdrawal from account number %s"
                        .formatted(transactionDto.getAccountNumber()));

            handleWithdrawal(transactionDto, account, totalAvailable);

        } else {
            handleDeposit(transactionDto, account);
        }

        account = accountRepository.save(account);
        return account.toDto();
    }

    private static void handleDeposit(TransactionDto transactionDto, Account account) {
        BigDecimal remainder = transactionDto.getValue()
                .subtract(account.getMaxLimit().subtract(account.getAvailableLimit()));

        account.setAvailableLimit(
                account.getAvailableLimit()
                        .add(
                                transactionDto.getValue()
                                        .subtract(remainder)
                        ));
        if (remainder.compareTo(new BigDecimal("0.0")) > 0)
            account.setBalance(account.getBalance().add(remainder));
    }

    private static void handleWithdrawal(TransactionDto transactionDto, Account account, BigDecimal totalAvailable) {
        if (totalAvailable.compareTo(transactionDto.getValue()) >= 0) {

            BigDecimal remainder = transactionDto.getValue().subtract(account.getBalance());
            account.setBalance(
                    account.getBalance()
                            .subtract(
                                    transactionDto.getValue()
                                            .subtract(remainder)
                            ));
            account.setAvailableLimit(
                    account.getAvailableLimit().subtract(remainder)
            );
        } else throw new IllegalStateException("Insufficient funds, balance is %s, special limit is %s"
                .formatted(account.getBalance(), account.getAvailableLimit()));
    }
}
