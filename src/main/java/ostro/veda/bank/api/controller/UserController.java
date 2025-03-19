package ostro.veda.bank.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ostro.veda.bank.api.dto.UserDto;
import ostro.veda.bank.api.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserInformation(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserInformation(username));
    }
}