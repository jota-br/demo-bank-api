package ostro.veda.bank.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {

    private String user;
    private String token;
}
