package pl.deska.springbootnotesapp.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import pl.deska.springbootnotesapp.model.Note;

import java.util.List;
import java.util.Optional;

@Repository
public class NoteRepoImpl {

    private NoteRepository noteRepo;

    @Autowired
    public NoteRepoImpl(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }


    public Note save(Note note){
        return noteRepo.save(note);
    }

    public List<Note> findAll(){
        return noteRepo.findAll();
    }

    public Optional<Note> findNoteByTitleContaining(String title){
        return noteRepo.findNoteByTitleContaining(title);
    }

    public Optional<Note> findById(Long id){
        return noteRepo.findById(id);
    }

    public void update(Note note){
        noteRepo.save(note);
    }

    public void delete(Note note){
        noteRepo.delete(note);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDb(){
        noteRepo.save(new Note("Lista zakupów", "musze zrobic zakupy"));
        noteRepo.save(new Note("PPJ 03", "Operacje bitowe, pętle for, while..."));
        noteRepo.save(new Note("Matematyka wzory", "lim -> x^2 * (x-2)"));
    }
}
