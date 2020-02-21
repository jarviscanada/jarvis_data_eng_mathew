package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "trader";
  final String idColumnName = "id";

  @Autowired
  public TraderDao(DataSource ds) {
    template = new JdbcTemplate(ds);
    simpleInsert = new SimpleJdbcInsert(template)
        .withTableName(tableName)
        .usingGeneratedKeyColumns(idColumnName);
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
    String updateQuery = "UPDATE " + tableName + " SET first_name=?, last_name=?, country=?, "
        + "email=? WHERE " + idColumnName + "=?";
    return template.update(updateQuery, entity.getFirstName(), entity.getLastName(),
        entity.getCountry(), entity.getEmail(), entity.getId());
  }
}
