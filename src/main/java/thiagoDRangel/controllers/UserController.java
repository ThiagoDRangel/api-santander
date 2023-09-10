package thiagoDRangel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thiagoDRangel.dto.UserDto;
import thiagoDRangel.interfaces.IUser;
import thiagoDRangel.models.User;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Users controller", description = "RESTful API Santander.")
public record UserController(IUser userService) {
    @GetMapping
    @Operation(summary = "Get all users", description = "Return a list of all registered users")
    @ApiResponses(value = { @ApiResponse (responseCode= "200", description = "getAll operation successful") })
    public ResponseEntity<List<UserDto>> findAll() {
        var users = userService.findAll();
        var userDto = users.stream().map(UserDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) { // trocar User por dto
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        var newUser = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUser);
    }
}
