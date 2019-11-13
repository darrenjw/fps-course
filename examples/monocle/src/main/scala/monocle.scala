/*
monocle.scala
Stub for Scala Monocle code
*/

object MonocleApp {

  case class Street(number: Int, name: String)
  case class Address(city: String, street: Street)
  case class Company(name: String, address: Address)
  case class Employee(name: String, company: Company)

  def main(args: Array[String]): Unit = {

    val employee = Employee("john", Company("awesome inc",
      Address("london", Street(23, "high street"))))

    println(employee)
    employee.copy(
      company = employee.company.copy(
        address = employee.company.address.copy(
          street = employee.company.address.street.copy(
            name = employee.company.address.street.name.capitalize
          ))))

    import monocle._
    import monocle.macros._

    val updated = GenLens[Employee](_.company.address.street.name).modify(
      _.capitalize)(employee)
    println(updated)

  }

}
