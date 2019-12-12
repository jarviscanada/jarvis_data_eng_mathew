package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

/**
 * A Data Access Object for looking up information about an order and the products ordered. Orders
 * are searched by Order ID, and the caller must supply a SQL Connection.
 */
public class OrderDao {

  private Logger daoLogger;

  public OrderDao() {
    daoLogger = LoggerFactory.getLogger(OrderDao.class);
  }

  /**
   * Using the supplied connection, queries the database for information about the given Order ID.
   *
   * @param orderID An int. The Order ID we want to look up
   * @param conn    an active SQL Connection
   * @return An order object if we were able to retrieve an order, otherwise null
   */
  public Order findById(int orderID, Connection conn) {
    String statement = "select  c.first_name, c.last_name, c.email, "
        + "o.order_id, o.creation_Timestamp, o.total_due, o.status, "
        + "s.first_name, s.last_name, s.email, "
        + "ol.quantity, "
        + "p.code, p.name, p.size, p.variety, p.price "
        + "from orders o "
        + "join customer c on o.customer_id = c.customer_id "
        + "join salesperson s on o.salesperson_id = s.salesperson_id "
        + "join order_item ol on ol.order_id = o.order_id "
        + "join product p on ol.product_id = p.product_id "
        + "where o.order_id = ?;";

    try {
      PreparedStatement ps = conn.prepareStatement(statement);
      ps.setInt(1, orderID);
      ResultSet results = ps.executeQuery();
      return processResults(results);
    } catch (SQLException sqlex) {
      daoLogger.error(MarkerFactory.getMarker("SQL"), sqlex.getMessage());
    }
    return null;
  }

  /**
   * Parses the result set into variables for creating a new order. Since an order can have one or
   * more products, an Order lookup can contain multiple rows with different product information
   *
   * @param results the ResultSet of our order lookup
   * @return a filled-in Order object.
   */
  private Order processResults(ResultSet results) {
    int quantity;
    String prodCode;
    String prodName;
    int prodSize;
    String prodVariety;
    double prodPrice;
    Order order = new Order();

    try {
      if (results.next()) {
        order.setCustFName(results.getString(1));
        order.setCustLName(results.getString(2));
        order.setCustEmail(results.getString(3));
        order.setOrderID(results.getInt(4));
        order.setCreationTimestamp(Timestamp.valueOf(results.getString(5)));
        order.setTotalDue(results.getDouble(6));
        order.setStatus(results.getString(7));
        order.setSalesFName(results.getString(8));
        order.setSalesLName(results.getString(9));
        order.setSalesEmail(results.getString(10));

        do {
          quantity = results.getInt(11);
          prodCode = results.getString(12);
          prodName = results.getString(13);
          prodSize = results.getInt(14);
          prodVariety = results.getString(15);
          prodPrice = results.getDouble(16);
          order.addOrderItem(quantity, prodCode, prodName, prodSize, prodVariety, prodPrice);
        } while (results.next());
        return order;
      }
    } catch (SQLException sqlex) {
      daoLogger.error(MarkerFactory.getMarker("SQL"), "SQL Results processing failed.");
      daoLogger.error(MarkerFactory.getMarker("SQL"), sqlex.getMessage());
    }
    return null;
  }
}
