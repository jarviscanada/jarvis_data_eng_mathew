package ca.jrvs.apps.jdbc;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcExecutor {

  OrderDao orderDB;
  Logger jdbcLogger;

  public JdbcExecutor() {
    DataSource ds = setupDataSource("jdbc:postgresql://localhost/hplussport", "postgres",
        "password");
    Order order = null;
    jdbcLogger = LoggerFactory.getLogger(JdbcExecutor.class);
    orderDB = new OrderDao();

    try {
      order = orderDB.findById(1001, ds.getConnection());
    } catch (SQLException sqlex) {
      jdbcLogger.error(sqlex.getMessage());
    }
    if (order != null) {
      jdbcLogger.info(order.toString());
    }
  }

  private DataSource setupDataSource(String url, String user, String pass) {
    BasicDataSource bds = new BasicDataSource();
    bds.setUrl(url);
    bds.setUsername(user);
    bds.setPassword(pass);
    return bds;
  }

  public static void main(String[] args) {
    new JdbcExecutor();
  }
}
