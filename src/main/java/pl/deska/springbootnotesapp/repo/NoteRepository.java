package pl.deska.springbootnotesapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.deska.springbootnotesapp.model.Note;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findNoteByTitleContaining(String title);

}
