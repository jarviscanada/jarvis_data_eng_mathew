package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao extends JdbcCrudDao<Position> {

  JdbcTemplate template;
  final String tableName = "position";
  final String idColumnName = "account_id";

  @Autowired
  public PositionDao(DataSource ds) {
    template = new JdbcTemplate(ds);
  }

  @Override
  JdbcTemplate getJdbcTemplate() {
    return template;
  }

  @Override
  SimpleJdbcInsert getSimpleJdbcInsert() {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public String getTableName() {
    return tableName;
  }

  @Override
  public String getIdColumnName() {
    return idColumnName;
  }

  @Override
  Class<Position> getEntityClass() {
    return Position.class;
  }

  public List<Position> findAllForId(Integer id) {
    String findQuery = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
    return getJdbcTemplate().query(findQuery,
        BeanPropertyRowMapper.newInstance(Position.class), id);
  }

  @Override
  public int updateEntity(Position entity) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public <S extends Position> S save(S entity) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public <S extends Position> Iterable<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public void deleteById(Integer id) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public void delete(Position entity) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public void deleteAll(Iterable<? extends Position> entities) {
    throw new UnsupportedOperationException("Position data is read-only");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Position data is read-only");
  }
}
