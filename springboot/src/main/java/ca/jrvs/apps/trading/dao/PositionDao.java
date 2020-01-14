package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class PositionDao extends JdbcCrudDao<Position> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "position";
  final String idColumnName = "account_id";

  @Autowired
  public PositionDao(DataSource ds) {
    template = new JdbcTemplate(ds);
    simpleInsert = new SimpleJdbcInsert(template).withTableName(tableName);
  }

  @Override
  JdbcTemplate getJdbcTemplate() {
    return template;
  }

  @Override
  SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleInsert;
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

  @Override
  public int updateEntity(Position entity) {
    return 0;
  }
}
