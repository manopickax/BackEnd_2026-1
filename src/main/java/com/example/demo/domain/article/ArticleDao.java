package com.example.demo.domain.article;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleDao {

    private final JdbcTemplate jdbcTemplate;

    public ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Article> rowMapper = (rs, rowNum) -> {
        Article article = new Article();
        article.setId(rs.getLong("id"));
        article.setTitle(rs.getString("title"));
        article.setContent(rs.getString("content"));
        article.setMemberId(rs.getLong("author_id"));
        article.setBoardId(rs.getLong("board_id"));
        Timestamp createdTs = rs.getTimestamp("created_date");
        article.setCreatedDate(createdTs != null ? createdTs.toLocalDateTime() : null);
        Timestamp modifiedTs = rs.getTimestamp("modified_date");
        article.setModifiedDate(modifiedTs != null ? modifiedTs.toLocalDateTime() : null);
        return article;
    };

    private static final String SELECT_ALL_COLUMNS =
            "SELECT id, author_id, board_id, title, content, created_date, modified_date FROM article";

    public List<Article> findAll() {
        return jdbcTemplate.query(SELECT_ALL_COLUMNS, rowMapper);
    }

    public List<Article> findByBoardId(Long boardId) {
        return jdbcTemplate.query(
                SELECT_ALL_COLUMNS + " WHERE board_id = ?", rowMapper, boardId);
    }

    public Optional<Article> findById(Long id) {
        List<Article> result = jdbcTemplate.query(
                SELECT_ALL_COLUMNS + " WHERE id = ?", rowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public boolean existsByMemberId(Long memberId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM article WHERE author_id = ?", Integer.class, memberId);
        return count != null && count > 0;
    }

    public boolean existsByBoardId(Long boardId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM article WHERE board_id = ?", Integer.class, boardId);
        return count != null && count > 0;
    }

    public Article save(Article article) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO article (author_id, board_id, title, content) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, article.getMemberId());
            ps.setLong(2, article.getBoardId());
            ps.setString(3, article.getTitle());
            ps.setString(4, article.getContent());
            return ps;
        }, keyHolder);
        article.setId(keyHolder.getKey().longValue());
        return article;
    }

    public Article update(Article article) {
        jdbcTemplate.update(
                "UPDATE article SET author_id = ?, board_id = ?, title = ?, content = ? WHERE id = ?",
                article.getMemberId(), article.getBoardId(),
                article.getTitle(), article.getContent(), article.getId());
        return article;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM article WHERE id = ?", id);
    }
}
