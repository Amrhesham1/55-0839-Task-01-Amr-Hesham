package s55_0839.t_20.Amr_Hesham.controllers;

import  s55_0839.t_20.Amr_Hesham.models.User;
import  s55_0839.t_20.Amr_Hesham.services.UserService;
import org.springframework.web.bind.annotation.*;
import s55_0839.t_20.Amr_Hesham.models.Note;
import s55_0839.t_20.Amr_Hesham.services.NoteService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final NoteService noteService;

    public UserController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/notes")
    public List<Note> getUserNotes(@PathVariable String id) {
        return noteService.getNotesByUserId(id);
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


}
