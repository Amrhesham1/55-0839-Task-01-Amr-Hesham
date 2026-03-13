package s55_0839.t_20.Amr_Hesham.services;



import s55_0839.t_20.Amr_Hesham.models.User;
import s55_0839.t_20.Amr_Hesham.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. List<User> getAllUsers()
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. User getUserById(String id)
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }

    // 3. User createUser(User user)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 4. User updateUser(String id, User user)
    public User updateUser(String id, User user) {
        return userRepository.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }

    // 5. void deleteUser(String id)
    public void deleteUser(String id) {
        boolean deleted = userRepository.deleteById(id);

        if (!deleted) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
            );
        }
    }

    // 6. GET /users/search?username=... needs this service method
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
    }
}
