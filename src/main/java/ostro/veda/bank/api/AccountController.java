package ostro.veda.bank.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.AccountTransactionDto;
import ostro.veda.bank.api.service.AccountServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> doTransaction(AccountTransactionDto accountTransactionDto) {
        return ResponseEntity.ok(accountService.doTransaction(accountTransactionDto));
    }
}
