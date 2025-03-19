package ostro.veda.bank.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ostro.veda.bank.api.dto.UserAuthDto;
import ostro.veda.bank.api.dto.UserSession;
import ostro.veda.bank.api.service.UserServiceImpl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserServiceImpl userService;

    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserSession> login(@RequestBody UserAuthDto userAuthDto)
            throws InvalidKeyException, NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.login(userAuthDto));
    }
}
