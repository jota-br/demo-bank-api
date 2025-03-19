package ostro.veda.bank.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.TransactionDto;
import ostro.veda.bank.api.dto.TransactionType;
import ostro.veda.bank.api.model.Account;
import ostro.veda.bank.api.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
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
