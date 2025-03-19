package ostro.veda.bank.api.service;

import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.TransactionDto;

public interface AccountService {

    AccountDto doTransaction(TransactionDto transactionDto);
}
