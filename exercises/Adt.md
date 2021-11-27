# Part 1: ADTs and typeclasses

## Practical exercises

These exercises are to be undertaken following the presentation of the material from Part 1 of the course.

### 1. Setup

* Make sure that you are set up to write, compile and run code using the libraries and versions used in this course:

Scala 3.0.1, Sbt 1.5.1, Cats 2.6.1, Monocle 3.0.0, Discipline 1.0.9, MUnit 0.7.29, MDoc 2.2.22

I have a [sbt giter8 project template](https://github.com/darrenjw/fps.g8) that should make this very easy. Assuming that you have [sbt](https://www.scala-sbt.org/) installed, you should just be able to do:
```bash
sbt new darrenjw/fps.g8
```
to create a new project template with all of the required dependencies in the current working directory. You can then enter this directory and spin up a REPL with `sbt console`, or import the project *as an sbt project* into your IDE/editing environment of choice.

Try pasting the following into a REPL to make sure that you are properly set up:
```scala
import cats.*
import cats.implicits.*
Map("a" -> 1, "b" -> 2) |+| Map("b" -> 3, "c" -> 4)
```

Note that if for some reason you can't or don't want to use `sbt new`, there is an [example blank template](../app-template) available for you to copy, edit and import. Note also that if you are using an IDE like IntelliJ, you should at least edit the project name in `build.sbt` before importing, as IntelliJ gets confused if you try to import multiple projects with the same name.

* Explore this repo/tarball. Have a browse around the directories to see what is available. 

### 2. Paste fragments

* Look at the code in the [fragments](../fragments/) directory. This directory contains all of the code from the notes, automatically extracted. The files associated with Part 1 of the course are of the form `part1s-*.scala`.
    * [tapf](../fragments/part1s-tapf.scala)
    * [adt](../fragments/part1s-adt.scala)
    * [mon](../fragments/part1s-mon.scala)
    * [assoc](../fragments/part1s-assoc.scala)
    * [tc](../fragments/part1s-tc.scala)

Go through the fragment files in turn, pasting code chunks into a REPL, one at a time, making sure they work, and making sure you understand what is going on. Use the REPL to explore things you aren't quite sure about.

* When using Cats, you will want the [Cats website](https://typelevel.org/cats/) open in your browser - especially the tutorial documentation for the [type classes](https://typelevel.org/cats/typeclasses.html) and [data types](https://typelevel.org/cats/datatypes.html), but also the [API docs](https://typelevel.org/cats/api/cats/).

### 3. Some scala exercises

* [Scala exercises](https://www.scala-exercises.org/) has some nice web-based interactive exercises to help learn some Scala libraries and programming concepts.
* If you are new to FP ideas, it would be worth working through the first few parts of the [FP in Scala](https://www.scala-exercises.org/fp_in_scala/getting_started_with_functional_programming) exercises. Start with "Getting started with FP", and also do "Functional data structures". We'll come back to some of the other bits later.
* Work through the [Cats exercises](https://www.scala-exercises.org/cats/semigroup) for "Semigroup" and "Monoid". Again, we'll come back to some of the other bits later.

### 4. Monoid instance

* Create an ADT of the form
```scala
case class LeavingPresent(names: List[String], fund: Double)
```
which tracks the contributors to a leaving fund and the total contribution, but not the sizes of the individual contributions.
* Create a `Monoid` instance for `LeavingPresent`.
* Make sure you can combine contributions from different groups, using `|+|`. eg.
```scala
LeavingPresent(List("Angela", "Brian"), 12.50) |+| LeavingPresent(List("Charles", "Diana", "Edward"), 45.75)
```



#### eof

