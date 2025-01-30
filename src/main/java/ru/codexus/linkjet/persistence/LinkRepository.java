package ru.codexus.linkjet.persistence;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.codexus.linkjet.dto.Link;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class LinkRepository {

    private static final String SELECT_SQL = "SELECT * FROM link WHERE id=:id";
    private static final String INSERT_SQL = "INSERT INTO link (id, url, expired_in) VALUES (:id, :url, :expires_in)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LinkRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Link> findById(String id) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_SQL, params, Link::mapRow));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public int createLink(String id, String url, LocalDateTime expiresIn) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("url", url)
            .addValue("expires_in", expiresIn);

        return jdbcTemplate.update(INSERT_SQL, params);
    }
}
