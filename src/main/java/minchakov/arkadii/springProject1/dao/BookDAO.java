package minchakov.arkadii.springProject1.dao;

import minchakov.arkadii.springProject1.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO implements DAO<Book> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Book> rowMapper;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, RowMapper<Book> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Optional<Book> get(Long id) {
        return jdbcTemplate.query(
            "select * from book where id = ?",
            rowMapper,
            id
        ).stream().findAny();
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select * from book", rowMapper);
    }

    public void save(Book book) {
        jdbcTemplate.update(
            "insert into book (title, author, publication_year, person_id) values (?, ?, ?, ?)",
            book.getTitle(),
            book.getAuthor(),
            book.getPublicationYear(),
            book.getPersonId()
        );
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update(
            "update book set title = ?, author = ?, publication_year = ?, person_id = ? where id = ?",
            book.getTitle(),
            book.getAuthor(),
            book.getPublicationYear(),
            book.getPersonId(),
            book.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }

    public List<Book> getByPersonId(Long personId) {
        return jdbcTemplate.query(
            "select * from book where person_id = ?",
            rowMapper,
            personId
        );
    }
}
