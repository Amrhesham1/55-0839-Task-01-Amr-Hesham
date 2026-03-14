package s55_0839.t_20.Amr_Hesham.services;



import s55_0839.t_20.Amr_Hesham.models.Note;
import s55_0839.t_20.Amr_Hesham.repositories.NoteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    // constructor injection
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    // 1. List<Note> getAllNotes()
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // 2. Note getNoteById(String id)
    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Note not found"
                ));
    }

    // 3. List<Note> getNotesByUserId(String userId)
    public List<Note> getNotesByUserId(String userId) {
        List<Note> notes = noteRepository.findByUserId(userId);

        if (notes.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No notes found for this user"
            );
        }

        return notes;
    }

    // 4. Note createNote(Note note)
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    // 5. Note updateNote(String id, Note note)
    public Note updateNote(String id, Note note) {
        return noteRepository.update(id, note)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Note not found"
                ));
    }

    // 6. void deleteNote(String id)
    public void deleteNote(String id) {
        boolean deleted = noteRepository.deleteById(id);

        if (!deleted) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Note not found"
            );
        }
    }
}
