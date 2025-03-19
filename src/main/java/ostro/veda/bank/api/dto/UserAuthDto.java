package ostro.veda.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAuthDto {

    private final long id;
    private final String name;
    private final String password;
}
