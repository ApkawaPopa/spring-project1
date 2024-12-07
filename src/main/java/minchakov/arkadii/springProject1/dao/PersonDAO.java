package minchakov.arkadii.springProject1.dao;

import minchakov.arkadii.springProject1.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO implements DAO<Person> {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Person> rowMapper;

    public PersonDAO(JdbcTemplate jdbcTemplate, RowMapper<Person> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public Optional<Person> get(Long id) {
        return jdbcTemplate.query(
            "select * from person where id = ?",
            rowMapper,
            id
        ).stream().findAny();
    }

    @Override
    public List<Person> getAll() {
        return jdbcTemplate.query("select * from person", rowMapper);
    }

    public void save(Person person) {
        jdbcTemplate.update(
            "insert into person(full_name, birth_year) values (?, ?)",
            person.getFullName(),
            person.getBirthYear()
        );
    }

    @Override
    public void update(Person person) {
        jdbcTemplate.update(
            "update person set full_name = ?, birth_year = ? where id = ?",
            person.getFullName(),
            person.getBirthYear(),
            person.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from person where id = ?", id);
    }

    public Optional<Person> find(String fullName) {
        return jdbcTemplate.query(
            "select * from person where full_name = ?",
            rowMapper,
            fullName
        ).stream().findAny();
    }
}
