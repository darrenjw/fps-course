# Part 4: Deferred evaluation, free monads and monad transformers

## Practical exercises

### 1. Code fragments

Go through the [code fragments](../fragments/) for Part 4:

* [free](../fragments/part4s-free.scala)
* [io](../fragments/part4s-io.scala)
* [test](../fragments/part4s-test.scala)
* [trans](../fragments/part4s-trans.scala)

### 2. Scala exercises

* Do the [ScalaCheck](https://www.scala-exercises.org/scalacheck/properties) exercises, to learn a bit more about property based testing.
* Go back to the [Cats exercises](https://www.scala-exercises.org/cats/semigroup), and finish off exercises you are interested in.
* Go back to the [FP in Scala](https://www.scala-exercises.org/fp_in_scala/getting_started_with_functional_programming) exercises, and continue with where you last left off.

### 3. Cats IO

* Write your own stand-alone Cats IO "Hello world" application.
* Start with a program with a `main` function that creates an IO and then runs it.
* When you have got that working, re-factor it as an `IOApp`.
* Extend your application to read in your name and age from console, and then print them back to you - use a `for` expression to chain together the input and output `IO`s.

### 4. Cats Free

* Read through the Cats documentation for the [Free monad](https://typelevel.org/cats/datatypes/freemonad.html).
* This considers a slightly more complex example than we considered - we concentrated on a pure monad, but in general we implement free monads for types with additional functionality. To defer implementation, this additional functionality must also be reified in an appropriate ADT.
* Create a stand-alone app implementing the example from the Cats documentation - make sure you understand all the parts.


