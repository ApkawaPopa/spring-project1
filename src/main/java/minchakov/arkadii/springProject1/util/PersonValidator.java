package minchakov.arkadii.springProject1.util;

import minchakov.arkadii.springProject1.dao.PersonDAO;
import minchakov.arkadii.springProject1.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var person = (Person) target;

        var found = personDAO.find(person.getFullName());

        if (found.isPresent() && !Objects.equals(found.get().getId(), person.getId())) {
            errors.rejectValue("fullName", "", "Человек с таким именем уже есть.");
        }
    }
}
