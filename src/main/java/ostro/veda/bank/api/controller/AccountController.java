package ostro.veda.bank.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.TransactionDto;
import ostro.veda.bank.api.service.AccountServiceImpl;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> doTransaction(TransactionDto transactionDto) {
        return ResponseEntity.ok(accountService.doTransaction(transactionDto));
    }
}
