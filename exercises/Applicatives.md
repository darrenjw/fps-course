# Part 3: Applicative, comonads and optics

## Practical exercises

### 1. Code fragments

Go through the [code fragments](../fragments/) for Part 3:

* [applic](../fragments/part3s-applic.scala)
* [comon](../fragments/part3s-comon.scala)
* [lens](../fragments/part3s-lens.scala)

### 2. Scala exercises

* Return to the [Cats exercises](https://www.scala-exercises.org/cats/apply) and do "Apply", "Applicative", "Foldable", "Traverse" and "Validated".
* Look at the [Monocle exercises](https://www.scala-exercises.org/monocle/iso), and work through "Iso", "Lens" and "Prism".

### 3. Traversing futures

* Write a function
```scala
def getPage(url: String): Future[String]
```
with a regular Scala `Future` to download a requested web page, so that the string in the output represents page content. Just use `scala.io.Source.fromURL(url).mkString` to grab the page content. You function will return immediately, and download the page in another thread.
* Write a function
```scala
def getPages(urls: List[String]): Future[List[String]]
```
using `traverse` with `getPage` to grab a bunch of pages *in parallel*.

### 4. Optics with monocle

* Revisit the illustrative example from the monocle website:
```scala
case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

val employee = Employee("john", Company("awesome inc", Address("london", Street(23, "high street"))))
```
Develop and compose some appropriate lenses for modifying various parts of the data structure of an employee. Think about structuring your code so that lens creation happens only once, no matter how many times data structures are accessed.

#### eof
