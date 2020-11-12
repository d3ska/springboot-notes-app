package pl.deska.springbootnotesapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.deska.springbootnotesapp.model.Note;
import pl.deska.springbootnotesapp.repo.NoteRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class NoteController {

    private NoteRepository noteRepo;
    private String message;

    @Autowired
    public NoteController(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public String home(Model model) {
        List<Note> notes = noteRepo.findAll();
        if (!notes.isEmpty()) {
            model.addAttribute("notes", notes);
            message = "";
        } else {
            model.addAttribute("notes", new ArrayList<>());
            message = "Empty notes";
        }
        model.addAttribute("searchingNote", new Note());
        model.addAttribute("message", message);
        return "notes";
    }


    @PostMapping("/search")
    public String search(@ModelAttribute Note note, Model model) {
        Optional<Note> optionalNote = noteRepo.findNoteByTitleContaining(note.getTitle());
        if (optionalNote.isPresent()) {
            model.addAttribute("notes", Collections.singleton(optionalNote.get()));
        } else {
            message = "There is no note with title " + note.getTitle();
            model.addAttribute("message", message);
            model.addAttribute("notes", new ArrayList<>());
        }
        model.addAttribute("searchingNote", new Note());
        return "notes";
    }


    @GetMapping("/add")
    public String addNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "add-note-form";
    }


    @PostMapping("/addNote")
    public String addNotee(@ModelAttribute Note note) {
        note.setCreationTime();
        noteRepo.save(note);
        return "redirect:/";
    }


    @GetMapping("/update/{id}")
    public String updateNoteForm(@PathVariable Long id, Model model) {
        Note note = noteRepo.findById(id).get();
        model.addAttribute("note", note);
        return "update-note-form";

    }


    @PostMapping("/updateNote")
    public String updateNote(@ModelAttribute Note noteToUpdate) {
        noteToUpdate.setCreationTime();
        noteRepo.save(noteToUpdate);
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteRepo.deleteById(id);
        return "redirect:/";
    }


}

