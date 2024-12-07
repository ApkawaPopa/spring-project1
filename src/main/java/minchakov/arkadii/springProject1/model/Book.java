package minchakov.arkadii.springProject1.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class Book {
    private Long id;

    @NotBlank(message = "Название не должно быть пустым")
    @Length(max = 100, message = "Длина названия должна быть не более 100 символов")
    private String title;

    @Length(max = 100, message = "Длина имени автора должна быть не более 100 символов")
    private String author;

    @PositiveOrZero(message = "Год издания не может быть отрицательным")
    private Long publicationYear;

    private Long personId;

    public Book() {
    }

    public Book(Long id, String title, String author, Long publicationYear, Long personId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Long publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
