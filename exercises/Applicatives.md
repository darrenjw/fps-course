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
with a regular Scala `Future` to download a requested web page, so that the string in the output represents page content. Just use `scala.io.Source.fromURL(url).mkString` to grab the page content.
* Write a function
```scala
def getPages(urls: List[String]): Future[List[String]]
```
using `traverse` to grab a bunch of pages *in parallel*.

### 4. Optics with monocle

* 

#### eof
