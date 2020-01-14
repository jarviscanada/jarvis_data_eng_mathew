package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "security_order";
  final String idColumnName = "id";

  @Autowired
  public SecurityOrderDao(DataSource ds) {
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
  Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
  }

  @Override
  public int updateEntity(SecurityOrder entity) {
    return 0;
  }
}