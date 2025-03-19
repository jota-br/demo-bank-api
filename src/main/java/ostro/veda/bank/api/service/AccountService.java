package ostro.veda.bank.api.service;

import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.AccountTransactionDto;

public interface AccountService {

    AccountDto doTransaction(AccountTransactionDto accountTransactionDto);
}
