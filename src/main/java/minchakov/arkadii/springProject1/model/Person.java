package minchakov.arkadii.springProject1.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class Person {
    private Long id;

    @NotBlank(message = "ФИО не может быть пустым")
    @Length(max = 100, message = "Длина ФИО должна быть не более 100 символов")
    private String fullName;

    @NotNull(message = "Год рождения не может быть пустым")
    @PositiveOrZero(message = "Год рождения не может быть отрицательным")
    private Long birthYear;

    public Person() {
    }

    public Person(Long id, String fullName, Long birthYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Long birthYear) {
        this.birthYear = birthYear;
    }
}
