package ostro.veda.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ostro.veda.bank.api.model.AccountType;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountDto {

    private final long id;
    private final String accountNumber;
    private final BigDecimal balance;
    private final AccountType accountType;
    private final BigDecimal availableLimit;
    private final BigDecimal maxLimit;
}
