package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return id == employee.id &&
        age == employee.age &&
        salary == employee.salary &&
        name.equals(employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, age, salary, name);
  }

  public static void main(String[] args) {
    Map<Employee, List<String>> pastEmployers = new HashMap<>();

    Employee emp1 = new Employee("Bob", 123454321, 35, 360000);
    pastEmployers.put(emp1, Arrays.asList("Company1", "Company2", "Company3"));

    Employee emp2 = new Employee("Steve", 123123123, 22, 45200);
    pastEmployers.put(emp2, Arrays.asList("Company2", "Company3", "Company5", "Company7"));

    System.out.println("Bob hashcode: " + emp1.hashCode());
    System.out.println("Bob past employers: " + pastEmployers.get(emp1));
    System.out.println("Steve hashcode: " + emp2.hashCode());
    System.out.println("Steve past employers" + pastEmployers.get(emp2));
  }
}
