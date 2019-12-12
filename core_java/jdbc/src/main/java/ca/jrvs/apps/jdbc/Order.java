package ca.jrvs.apps.jdbc;

import java.sql.Timestamp;
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
 *   <li>order's creation Timestamp</li>
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

  protected static final String DB_CUSTFNAME = "c.first_name";
  protected static final String DB_CUSTLNAME = "c.last_name";
  protected static final String DB_CUSTEMAIL = "c.email";
  protected static final String DB_ORDERID = "o.order_id";
  protected static final String DB_CREATIONDATE = "o.creation_date";
  protected static final String DB_TOTALDUE = "o.total_due";
  protected static final String DB_STATUS = "o.status";
  protected static final String DB_SALESFNAME = "s.first_name";
  protected static final String DB_SALESLNAME = "s.last_name";
  protected static final String DB_SALESEMAIL = "s.email";

  private String custFName;
  private String custLName;
  private String custEmail;
  private int orderID;
  private Timestamp creationDate;
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

  // Constructor that builds most of the order. The ordered products must be added with AddOrderItem
  protected Order(String custFName, String custLName, String custEmail, int orderID,
      Timestamp creationDate, double totalDue, String status, String salesFName,
      String salesLName,
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

  protected static class OrderedProduct {

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

  public String getCustFName() {
    return custFName;
  }

  public void setCustFName(String custFName) {
    this.custFName = custFName;
  }

  public String getCustLName() {
    return custLName;
  }

  public void setCustLName(String custLName) {
    this.custLName = custLName;
  }

  public String getCustEmail() {
    return custEmail;
  }

  public void setCustEmail(String custEmail) {
    this.custEmail = custEmail;
  }

  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public double getTotalDue() {
    return totalDue;
  }

  public void setTotalDue(double totalDue) {
    this.totalDue = totalDue;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSalesFName() {
    return salesFName;
  }

  public void setSalesFName(String salesFName) {
    this.salesFName = salesFName;
  }

  public String getSalesLName() {
    return salesLName;
  }

  public void setSalesLName(String salesLName) {
    this.salesLName = salesLName;
  }

  public String getSalesEmail() {
    return salesEmail;
  }

  public void setSalesEmail(String salesEmail) {
    this.salesEmail = salesEmail;
  }

  public List<OrderedProduct> getOrderedItems() {
    return products;
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
