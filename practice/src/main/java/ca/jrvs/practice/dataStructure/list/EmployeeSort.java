package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeSort {

  List<Employee> employees;

  public EmployeeSort() {
    employees = new ArrayList<>();
    employees.add(new Employee("Steve", 1, 25, 320000L));
    employees.add(new Employee("Paul", 22, 33, 520000L));
    employees.add(new Employee("Mark", 5, 44, 4000000L));
    employees.add(new Employee("Andrea", 15, 19, 290000L));
    System.out.println("Initial Employee List");
    employees.forEach(x -> System.out.println(x.toString()));
  }

  public void sortEmployeesComparator() {
    EmployeeComparator comparator = new EmployeeComparator();
    System.out.println("Employees sorted by ID via inner comparator");
    employees.sort(comparator);
    System.out.println(employees);
  }

  public void sortEmployeesComparable() {
    List<ComparableEmployee> comparableEmployees = new ArrayList<>();
    for (Employee e : employees) {
      comparableEmployees.add(new ComparableEmployee(
          e.getName(), e.getId(), e.getAge(), e.getSalary()));
    }
    Collections.sort(comparableEmployees);
    System.out.println("Employees sorted by first initial using Comparable");
    System.out.println(comparableEmployees);
  }

  static class EmployeeComparator implements Comparator<Employee> {

    public int compare(Employee employee1, Employee employee2) {
      int id1 = employee1.getId();
      int id2 = employee2.getId();
      if (id1 < id2) {
        return -1;
      } else if (id1 > id2) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  static class ComparableEmployee extends Employee implements Comparable<Employee> {

    public ComparableEmployee(String name, int id, int age, long salary) {
      super(name, id, age, salary);
    }

    @Override
    public int compareTo(Employee employee) {
      char emp1Initial = this.getName().charAt(0);
      char emp2Initial = employee.getName().charAt(0);
      if (emp1Initial < emp2Initial) {
        return -1;
      } else if (emp1Initial > emp2Initial) {
        return 1;
      } else {
        return 0;
      }
    }
  }

  public static void main(String[] args) {
    EmployeeSort employeeSort = new EmployeeSort();
    employeeSort.sortEmployeesComparator();
    employeeSort.sortEmployeesComparable();
  }
}
