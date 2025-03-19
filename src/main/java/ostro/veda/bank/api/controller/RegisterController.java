package ostro.veda.bank.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.dto.UserAuthDto;
import ostro.veda.bank.api.service.UserServiceImpl;

import java.net.URI;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserServiceImpl userService;

    public RegisterController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@RequestBody UserAuthDto userAuthDto) throws NoSuchAlgorithmException {
        UserDto userDto = userService.createNewUser(userAuthDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/users/{id}")
                .buildAndExpand(userDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(userDto);
    }
}
