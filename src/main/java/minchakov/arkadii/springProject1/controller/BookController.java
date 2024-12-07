package minchakov.arkadii.springProject1.controller;

import minchakov.arkadii.springProject1.dao.BookDAO;
import minchakov.arkadii.springProject1.dao.PersonDAO;
import minchakov.arkadii.springProject1.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "book/index";
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("book") Book book) {
        return "book/create";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            return "book/create";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String readPage(@PathVariable Long id, Model model) {
        var book = bookDAO.get(id).orElse(null);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        var person = personDAO.get(book.getPersonId());
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        } else {
            model.addAttribute("people", personDAO.getAll());
        }
        return "book/read";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookDAO.get(id).orElse(null));
        return "book/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult errors) {
        if (errors.hasErrors()) {
            return "book/update";
        }

        bookDAO.update(book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/change_owner")
    public String change_owner(@PathVariable Long id, @RequestParam(value = "personId", required = false) Long personId) {
        var book = bookDAO.get(id).orElse(null);
        if (book != null) {
            book.setPersonId(personId);
            bookDAO.update(book);
        }
        return "redirect:/books/{id}";
    }
}
