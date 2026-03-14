package s55_0839.t_20.Amr_Hesham.controllers;



import s55_0839.t_20.Amr_Hesham.models.Note;
import s55_0839.t_20.Amr_Hesham.services.NoteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    // constructor injection
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // 1. GET /notes
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    // 2. GET /notes/{id}
    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    // 3. POST /notes
    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    // 4. GET /notes/search?title=...
    @GetMapping("/search")
    public List<Note> searchNotesByTitle(@RequestParam String title) {
        return noteService.getAllNotes().stream()
                .filter(note ->
                        note.getTitle() != null &&
                                note.getTitle().toLowerCase().contains(title.toLowerCase())
                )
                .toList();
    }

    // 5. PUT /notes/{id}
    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    // 6. DELETE /notes/{id}
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }
}