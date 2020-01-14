package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class TraderDao extends JdbcCrudDao<Trader> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "trader";
  final String idColumnName = "id";

  @Autowired
  public TraderDao(DataSource ds) {
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
  Class<Trader> getEntityClass() {
    return Trader.class;
  }

  @Override
  public int updateEntity(Trader entity) {
    return 0;
  }
}
