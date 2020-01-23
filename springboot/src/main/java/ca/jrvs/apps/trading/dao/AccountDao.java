package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "account";
  final String idColumnName = "id";

  @Autowired
  public AccountDao(DataSource ds) {
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
  Class<Account> getEntityClass() {
    return Account.class;
  }

  @Override
  public int updateEntity(Account entity) {
    String updateQuery = "UPDATE " + tableName + " SET amount=? WHERE " + idColumnName + "=?";
    return template.update(updateQuery, entity.getAmount(), entity.getId());
  }
}
