package s55_0839.t_20.Amr_Hesham.repositories;
import s55_0839.t_20.Amr_Hesham.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    private List<User> users;
    private java.io.File jsonFile;

    public UserRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/users.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read users.json");
        }

        try {
            this.jsonFile = new java.io.File(getClass().getResource("/users.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/users.json");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.users = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            this.users = new ArrayList<>();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to read users.json",
                    e
            );
        }
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId() != null && user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername() != null
                        && user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User save(User user) {
        user.setId(UUID.randomUUID().toString());

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }

        users.add(user);

        try {
            new ObjectMapper().writeValue(jsonFile, users);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to write users.json",
                    e
            );
        }

        return user;
    }

    public Optional<User> update(String id, User updated) {
        Optional<User> existingOptional = findById(id);

        if (existingOptional.isEmpty()) {
            return Optional.empty();
        }

        User existingUser = existingOptional.get();

        existingUser.setUsername(updated.getUsername());
        existingUser.setEmail(updated.getEmail());

        try {
            new ObjectMapper().writeValue(jsonFile, users);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update users.json",
                    e
            );
        }

        return Optional.of(existingUser);
    }

    public boolean deleteById(String id) {
        boolean removed = users.removeIf(user -> user.getId() != null && user.getId().equals(id));

        if (removed) {
            try {
                new ObjectMapper().writeValue(jsonFile, users);
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to update users.json after delete",
                        e
                );
            }
        }

        return removed;
    }
}
