package com.example.demo.domain.board;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardDao {

    private final JdbcTemplate jdbcTemplate;

    public BoardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Board> rowMapper = (rs, rowNum) -> {
        Board board = new Board();
        board.setId(rs.getLong("id"));
        board.setName(rs.getString("name"));
        return board;
    };

    public List<Board> findAll() {
        return jdbcTemplate.query("SELECT id, name FROM board", rowMapper);
    }

    public Optional<Board> findById(Long id) {
        List<Board> result = jdbcTemplate.query(
                "SELECT id, name FROM board WHERE id = ?", rowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM board WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    public Board save(Board board) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO board (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, board.getName());
            return ps;
        }, keyHolder);
        board.setId(keyHolder.getKey().longValue());
        return board;
    }

    public Board update(Board board) {
        jdbcTemplate.update(
                "UPDATE board SET name = ? WHERE id = ?",
                board.getName(), board.getId());
        return board;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM board WHERE id = ?", id);
    }
}
