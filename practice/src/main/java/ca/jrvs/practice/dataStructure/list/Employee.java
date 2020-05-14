package ca.jrvs.practice.dataStructure.list;

public class Employee {

  private String name;
  private int id;
  private int age;
  private long salary;

  public Employee(String name, int id, int age, long salary) {
    this.name = name;
    this.id = id;
    this.age = age;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "Employee{"
        + "name='" + name + '\''
        + ", id=" + id
        + ", age=" + age
        + ", salary=" + salary
        + '}';
  }
}
