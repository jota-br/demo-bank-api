package ostro.veda.bank.api.service;

import ostro.veda.bank.api.dto.UserDto;

public interface UserService {

    UserDto createNewUser(UserDto userDto);
}
