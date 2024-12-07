package minchakov.arkadii.springProject1.controller;

import minchakov.arkadii.springProject1.dao.BookDAO;
import minchakov.arkadii.springProject1.dao.PersonDAO;
import minchakov.arkadii.springProject1.model.Person;
import minchakov.arkadii.springProject1.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(BookDAO bookDAO, PersonDAO personDAO, PersonValidator personValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.getAll());
        return "person/index";
    }

    @GetMapping("/create")
    public String createPage(@ModelAttribute("person") Person person) {
        return "person/create";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult errors) {
        personValidator.validate(person, errors);

        if (errors.hasErrors()) {
            return "person/create";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String readPage(@PathVariable Long id, Model model) {
        var person = personDAO.get(id).orElse(null);
        if (person == null) {
            return "redirect:/people";
        }
        model.addAttribute("person", person);
        model.addAttribute("books", bookDAO.getByPersonId(person.getId()));
        return "person/read";
    }

    @GetMapping("/{id}/update")
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute("person", personDAO.get(id).orElse(null));
        return "person/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult errors) {
        personValidator.validate(person, errors);

        if (errors.hasErrors()) {
            return "person/update";
        }

        personDAO.update(person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
