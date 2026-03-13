
package  s55_0839.t_20.Amr_Hesham.controllers;

import  s55_0839.t_20.Amr_Hesham.models.User;
import  s55_0839.t_20.Amr_Hesham.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. GET /users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 2. GET /users/{id}
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // 3. POST /users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // 4. PUT /users/{id}
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // 5. DELETE /users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    // 6. GET /users/search?username=...
    @GetMapping("/search")
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }
}
