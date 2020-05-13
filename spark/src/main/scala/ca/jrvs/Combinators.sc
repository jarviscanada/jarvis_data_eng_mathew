import scala.reflect.io.File

/**
 * Count elements. Get first element. Get last element.
 * Get first 5 elements. Get last 5 elements.
 */
val ls = List.range(0,10)
println(ls.size)
println(ls.head)
println(ls.last)
println(ls.take(5))
println(ls.takeRight(5))

/**
 * Double each number from the numList and return a flatten list
 * e.g.res4: List[Int] = List(2, 3, 4)
 *
 * Compare flatMap VS ls.map().flatten
 */
val numList = List(List(1,2), List(3));
println(numList.flatMap(x => x.map(y => y*2)))
println(numList.map(x => x.map(y => y*2)).flatten) //IntelliJ complains to replace this with flatmap

/**
 * Sum List.range(1,11) in three ways
 * hint: sum, reduce, foldLeft
 *
 * Compare reduce and foldLeft
 * https://stackoverflow.com/questions/7764197/difference-between-foldleft-and-reduceleft-in-scala
 */
val summit = List.range(1,11)
println(summit.sum)
println(summit.foldLeft(0)((x :Int, y: Int) => x + y))
println(summit.reduceLeft((x, y) => x + y))

/**
 * Practice Map and Optional
 *
 * Map question1:
 *
 * Compare get vs getOrElse (Scala Optional)
 * countryMap.get("Amy");
 * countryMap.getOrElse("Frank", "n/a");
 */
val countryMap = Map("Amy" -> "Canada", "Sam" -> "US", "Bob" -> "Canada")
println(countryMap.get("Sam"))
println(countryMap.getOrElse("Steve", "Not Found"))

/**
 * Map question2:
 *
 * create a list of (name, country) tuples using `countryMap` and `names`
 * e.g. res2: List[(String, String)] = List((Amy,Canada), (Sam,US), (Eric,n/a), (Amy,Canada))
 */
val names = List("Amy", "Sam", "Eric", "Amy")
val nct = names.map(x => (x, countryMap.getOrElse(x,"n/a")))
println(nct.mkString(", "))

/**
 * Map question3:
 *
 * count number of people by country. Use `n/a` if the name is not in the countryMap  using `countryMap` and `names`
 * e.g. res0: scala.collection.immutable.Map[String,Int] = Map(Canada -> 2, n/a -> 1, US -> 1)
 * hint: map(get_value_from_map) ; groupBy country; map to (country,count)
 */
val ppc = names.foldLeft(Map.empty[String, Int])((x,y) => {
  val country = countryMap.getOrElse(y, "n/a")
  x + (country -> (x.getOrElse(country, 0) + 1))
})
println(ppc.mkString(", "))

/**
 * number each name in the list from 1 to n
 * e.g. res3: List[(Int, String)] = List((1,Amy), (2,Bob), (3,Chris))
 */
val names2 = List("Amy", "Bob", "Chris", "Dan")
val numberedNames = names2.foldLeft(List.empty[(Int, String)])((tuples, name) => {
  tuples :+ (tuples.size + 1, name) // Slow. Probably something better than Immutable list append
})

val numberedNames2 = (1 to names2.length).toList.zip(names2)

/**
 * SQL questions1:
 *
 * read file lines into a list
 * lines: List[String] = List(id,name,city, 1,amy,toronto, 2,bob,calgary, 3,chris,toronto, 4,dann,montreal)
 */
val employeeFile = File("/home/centos/dev/jarvis_data_eng_mathew/spark/src/main/resources/employees.csv")
var rows = employeeFile.lines().toList
rows = rows.drop(1)

/**
 * SQL questions2:
 *
 * Convert lines to a list of employees
 * e.g. employees: List[Employee] = List(Employee(1,amy,toronto), Employee(2,bob,calgary), Employee(3,chris,toronto), Employee(4,dann,montreal))
 */
case class Employee(id: Int, name: String, city: String, age: String)
var employees = rows.map(x => {
  val parts = x.split(',')
  Employee(parts(0).toInt, parts(1), parts(2), parts(3))
})
println(employees.mkString("; "))

/**
 * SQL questions3:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 *
 * result:
 * upperCity: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(2,bob,CALGARY,19), Employee(3,chris,TORONTO,20), Employee(4,dann,MONTREAL,21), Employee(5,eric,TORONTO,22))
 */
val upperCity = employees.map(x => x.city.toUpperCase)
println(upperCity.mkString(", "))

/**
 * SQL questions4:
 *
 * Implement the following SQL logic using functional programming
 * SELECT uppercase(city)
 * FROM employees
 * WHERE city = 'toronto'
 *
 * result:
 * res5: List[Employee] = List(Employee(1,amy,TORONTO,20), Employee(3,chris,TORONTO,20), Employee(5,eric,TORONTO,22))
 */
val empCityCap = employees.filter(x => x.city == "toronto").map(y => Employee(y.id, y.name, y.city.toUpperCase, y.age))
println(empCityCap.mkString(", "))

/**
 * SQL questions5:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city
 *
 * result:
 * cityNum: scala.collection.immutable.Map[String,Int] = Map(CALGARY -> 1, TORONTO -> 3, MONTREAL -> 1)
 */
val cityNum = employees.foldLeft(Map.empty[String, Int])((cityMap, employee) => {
  cityMap + (employee.city.toUpperCase -> (cityMap.getOrElse(employee.city.toUpperCase, 0) + 1))
})
println(cityNum.mkString(", "))

/**
 * SQL questions6:
 *
 * Implement the following SQL logic using functional programming
 *
 * SELECT uppercase(city), count(*)
 * FROM employees
 * GROUP BY city,age
 *
 * result:
 * res6: scala.collection.immutable.Map[(String, Int),Int] = Map((MONTREAL,21) -> 1, (CALGARY,19) -> 1, (TORONTO,20) -> 2, (TORONTO,22) -> 1)
 */
val cityAgeMap = employees.foldLeft(Map.empty[(String, Int), Int])((map, employee) => {
  val key = (employee.city.toUpperCase, employee.age.toInt)
  map + (key -> (map.getOrElse(key, 0)+1))
})
println(cityAgeMap.mkString(", "))

