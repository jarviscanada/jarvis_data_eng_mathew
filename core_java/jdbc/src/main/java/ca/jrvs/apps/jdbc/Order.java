package ca.jrvs.apps.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderData is a DTO that contains an Order for use with a database. An order may contain one or
 * more sets of product data and order_item quantities An Order row has the following fields
 * <ul>
 *   <li>customer's first name</li>
 *   <li>customer's last name</li>
 *   <li>customer's email</li>
 *   <li>order's order ID</li>
 *   <li>order's creation date</li>
 *   <li>order's amount due</li>
 *   <li>order's status</li>
 *   <li>salesperson's first name</li>
 *   <li>salesperson's last name</li>
 *   <li>salesperson's email</li>
 *   <li>order_item's quantity</li>
 *   <li>product's code</li>
 *   <li>product's name</li>
 *   <li>product's size</li>
 *   <li>product's variety</li>
 *   <li>product's price</li>
 * </ul>
 */
public class Order {

  protected static final String db_custFName = "c.first_name";
  protected static final String db_custLName = "c.last_name";
  protected static final String db_custEmail = "c.email";
  protected static final String db_orderID = "o.order_id";
  protected static final String db_creationDate = "o.creation_date";
  protected static final String db_totalDue = "o.total_due";
  protected static final String db_status = "o.status";
  protected static final String db_salesFName = "s.first_name";
  protected static final String db_salesLName = "s.last_name";
  protected static final String db_salesEmail = "s.email";

  private String custFName;
  private String custLName;
  private String custEmail;
  private int orderID;
  private String creationDate;
  private double totalDue;
  private String status;
  private String salesFName;
  private String salesLName;
  private String salesEmail;
  private List<OrderedProduct> products;

  // Default constructor.
  protected Order() {
    products = new ArrayList<>();
  }

  protected Order(String custFName, String custLName, String custEmail, int orderID,
      String creationDate, double totalDue, String status, String salesFName, String salesLName,
      String salesEmail) {
    this.custFName = custFName;
    this.custLName = custLName;
    this.custEmail = custEmail;
    this.orderID = orderID;
    this.creationDate = creationDate;
    this.totalDue = totalDue;
    this.status = status;
    this.salesFName = salesFName;
    this.salesLName = salesLName;
    this.salesEmail = salesEmail;
    products = new ArrayList<>();
  }

  private static class OrderedProduct {

    int quantity;
    String prodCode;
    String prodName;
    int prodSize;
    String prodVariety;
    double prodPrice;

    // Inner class OrderedProduct represents a set of Product information and quantity ordered.
    OrderedProduct(int quantity, String prodCode, String prodName, int prodSize, String prodVariety,
        double prodPrice) {
      this.quantity = quantity;
      this.prodCode = prodCode;
      this.prodName = prodName;
      this.prodSize = prodSize;
      this.prodVariety = prodVariety;
      this.prodPrice = prodPrice;
    }

    @Override
    public String toString() {
      return "OrderedProduct{"
          + "quantity=" + quantity
          + ", prodCode='" + prodCode + '\''
          + ", prodName='" + prodName + '\''
          + ", prodSize=" + prodSize
          + ", prodVariety='" + prodVariety + '\''
          + ", prodPrice=" + prodPrice
          + '}' + '\n';
    }
  }

  void addOrderItem(int quantity, String prodCode, String prodName, int prodSize,
      String prodVariety, double prodPrice) {
    products.add(new OrderedProduct(quantity, prodCode, prodName, prodSize, prodVariety,
        prodPrice));
  }

  @Override
  public String toString() {
    return "Order{"
        + "custFName='" + custFName + '\''
        + ", custLName='" + custLName + '\''
        + ", custEmail='" + custEmail + '\''
        + ", orderID=" + orderID
        + ", creationDate='" + creationDate + '\''
        + ", totalDue=" + totalDue
        + ", status='" + status + '\''
        + ", salesFName='" + salesFName + '\''
        + ", salesLName='" + salesLName + '\''
        + ", salesEmail='" + salesEmail + '\'' + '\n'
        + ", products=" + products.toString()
        + '}';
  }
}
