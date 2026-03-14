package s55_0839.t_20.Amr_Hesham.repositories;



import s55_0839.t_20.Amr_Hesham.models.Note;
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
public class NoteRepository {

    private List<Note> notes;
    private java.io.File jsonFile;

    public NoteRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/notes.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read notes.json");
        }

        try {
            this.jsonFile = new java.io.File(getClass().getResource("/notes.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/notes.json");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {});
        } catch (Exception e) {
            this.notes = new ArrayList<>();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to read notes.json",
                    e
            );
        }
    }

    public List<Note> findAll() {
        return notes;
    }

    public Optional<Note> findById(String id) {
        return notes.stream()
                .filter(note -> note.getId() != null && note.getId().equals(id))
                .findFirst();
    }

    public List<Note> findByUserId(String userId) {
        return notes.stream()
                .filter(note -> note.getUserId() != null && note.getUserId().equals(userId))
                .toList();
    }

    public Note save(Note note) {
        note.setId(UUID.randomUUID().toString());

        if (note.getCreatedAt() == null) {
            note.setCreatedAt(LocalDateTime.now());
        }

        notes.add(note);

        try {
            new ObjectMapper().writeValue(jsonFile, notes);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to write notes.json",
                    e
            );
        }

        return note;
    }

    public Optional<Note> update(String id, Note updated) {
        Optional<Note> existingOptional = findById(id);

        if (existingOptional.isEmpty()) {
            return Optional.empty();
        }

        Note existingNote = existingOptional.get();
        existingNote.setTitle(updated.getTitle());
        existingNote.setContent(updated.getContent());
        existingNote.setUserId(updated.getUserId());

        try {
            new ObjectMapper().writeValue(jsonFile, notes);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update notes.json",
                    e
            );
        }

        return Optional.of(existingNote);
    }

    public boolean deleteById(String id) {
        boolean removed = notes.removeIf(note -> note.getId() != null && note.getId().equals(id));

        if (removed) {
            try {
                new ObjectMapper().writeValue(jsonFile, notes);
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to update notes.json after delete",
                        e
                );
            }
        }

        return removed;
    }
}
