package ostro.veda.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserDto {

    private final long id;
    private final String name;
    private final String password;
    private final Set<AccountDto> accounts;
}
