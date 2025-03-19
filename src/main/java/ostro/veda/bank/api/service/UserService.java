package ostro.veda.bank.api.service;

import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.dto.UserAuthDto;
import ostro.veda.bank.api.dto.UserSession;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    UserDto getUserInformation(String username);

    UserDto createNewUser(UserAuthDto userAuthDto) throws NoSuchAlgorithmException;

    UserSession login(UserAuthDto userAuthDto) throws InvalidKeyException, NoSuchAlgorithmException;
}
