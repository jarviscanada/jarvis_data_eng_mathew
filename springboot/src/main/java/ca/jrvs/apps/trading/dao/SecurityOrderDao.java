package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

  JdbcTemplate template;
  SimpleJdbcInsert simpleInsert;
  final String tableName = "security_order";
  final String idColumnName = "id";

  @Autowired
  public SecurityOrderDao(DataSource ds) {
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
  Class<SecurityOrder> getEntityClass() {
    return SecurityOrder.class;
  }

  @Override
  public int updateEntity(SecurityOrder entity) {
    String updateQuery = "UPDATE " + tableName + " SET status=?, notes=? WHERE " + idColumnName
        + "=?";
    return template.update(updateQuery, entity.getStatus(), entity.getNotes(), entity.getId());
  }

  /**
   * Overloaded findAllById. Get all SecurityOrders associated with a specific account ID.
   *
   * @param id the Account ID to get orders from
   * @return A list of orders made by the given Account ID
   */
  public List<SecurityOrder> findAllById(Integer id) {
    String selectQuery = "SELECT * FROM " + tableName + " WHERE account_id=?";
    return template.query(selectQuery, BeanPropertyRowMapper.newInstance(SecurityOrder.class), id);
  }
}