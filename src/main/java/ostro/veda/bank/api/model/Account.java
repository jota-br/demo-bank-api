package ostro.veda.bank.api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ostro.veda.bank.api.dto.AccountDto;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String accountNumber;

    @Column(precision = 13, scale = 2)
    private BigDecimal balance;
    private AccountType accountType;

    @Column(precision = 13, scale = 2)
    private BigDecimal availableLimit;

    @Column(precision = 13, scale = 2)
    private BigDecimal maxLimit;

    public AccountDto toDto() {
        return new AccountDto(
                this.getId(), this.getAccountNumber(),
                this.getBalance(), this.getAccountType(), this.getAvailableLimit(), this.getMaxLimit());
    }
}

