package ostro.veda.bank.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ostro.veda.bank.api.dto.AccountDto;
import ostro.veda.bank.api.dto.UserDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Size(min = 8)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @Version
    private Integer version;

    public UserDto toDto() {

        Set<AccountDto> accountDtoSet = this.getAccounts().stream().map(Account::toDto).collect(Collectors.toSet());

        return new UserDto(this.getId(), this.getName(), accountDtoSet);
    }
}
