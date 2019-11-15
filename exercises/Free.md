# Part 4: Deferred evaluation, free monads and monad transformers

## Practical exercises

### 1. Code fragments

Go through the [code fragments](../fragments/) for Part 4:

* [free](../fragments/part4s-free.scala)
* [io](../fragments/part4s-io.scala)
* [test](../fragments/part4s-test.scala)
* [trans](../fragments/part4s-trans.scala)

### 2. Testing laws

The Cats website has a page on [law testing](https://typelevel.org/cats/typeclasses/lawtesting.html), explaining the use of cat-laws with discipline to check the required laws are satisfied by custom typeclass instances. This example is available for inspection in [tree.scala](../examples/tests/src/test/scala/tree.scala) in the examples directory. You can just `sbt test` from the `examples/tests` directory to run it.

### 3. Scala exercises

* Do the [ScalaCheck](https://www.scala-exercises.org/scalacheck/properties) exercises, to learn a bit more about property based testing.
* Go back to the [Cats exercises](https://www.scala-exercises.org/cats/semigroup), and finish off exercises you are interested in.
* Go back to the [FP in Scala](https://www.scala-exercises.org/fp_in_scala/getting_started_with_functional_programming) exercises, and continue with where you last left off.

### 4. Cats IO

* Write your own stand-alone Cats IO "Hello world" application.
* Start with a program with a `main` function that creates an IO and then runs it.
* When you have got that working, re-factor it as an `IOApp`.
* Extend your application to read in your name and age from console, and then print them back to you - use a `for` expression to chain together the input and output `IO`s.

### 5. Cats Free

* Read through the Cats documentation for the [Free monad](https://typelevel.org/cats/datatypes/freemonad.html).
* This considers a slightly more complex example than we considered - we concentrated on a pure monad, but in general we implement free monads for types with additional functionality. To defer implementation, this additional functionality must also be reified in an appropriate ADT.
* Create a stand-alone app implementing the example from the Cats documentation - make sure you understand all the parts.


### 6. Monad transformers and MTL

It is a bit clumsy to work with monad transformer stacks directly in Scala. MTL (monad transformer library) is an attempt to make it a bit easier. For an introduction, start with this [Comprehensive introduction to Cats-mtl](https://typelevel.org/blog/2018/10/06/intro-to-mtl.html).

