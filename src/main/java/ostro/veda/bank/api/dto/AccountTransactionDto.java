package ostro.veda.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountTransactionDto {

    private final String accountNumber;
    private final BigDecimal value;
    private final TransactionType transactionType;

}
