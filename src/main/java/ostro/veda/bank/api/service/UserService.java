package ostro.veda.bank.api.service;

import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.dto.UserRegisterDto;

public interface UserService {

    UserDto createNewUser(UserRegisterDto userRegisterDto);
}
