# Part 1: ADTs and typeclasses

## Practical exercises

These exercises are to be undertaken following the presentation of the material from Part 1 of the course.

### 1. Setup

* Make sure that you are set up to write, compile and run code using the libraries and versions used in this course:

Scala 2.12.10, Sbt 1.3.2, Cats 2.0.0, Simulacrum 1.0.0, Monocle 2.0.0, Discipline 1.0.0, ScalaTest 3.0.8

I have a [sbt giter8 project template](https://github.com/darrenjw/fps.g8) that should make this very easy. Assuming that you have [sbt]() installed, you should just be able to do:
```bash
sbt new darrenjw/fps.g8
```
to create a new project template with all of the required dependencies in the current working directory. You can then enter this directory and spin up a REPL with `sbt console`, or import the project *as an sbt project* into your IDE/editing environment of choice.

Try pasting the following into a REPL to make sure that you are properly set up:
```scala
import cats._
import cats.implicits._
Map("a" -> 1, "b" -> 2) |+| Map("b" -> 3, "c" -> 4)
```

Note that if for some reason you can't or don't want to use `sbt new`, there is an [example blank template](../app-template) available for you to copy, edit and import. Note also that if you are using an IDE like IntelliJ, you should at least edit the project name in `build.sbt` before importing, as IntelliJ gets confused if you try to import multiple projects with the same name.

* Explore this repo/tarball. Have a browse around the directories to see what is available. 

### 2. Paste fragments

* Look at the code in the [fragments](../fragments/) directory. This directory contains all of the code from the notes, automatically extracted. The files associated with Part 1 of the course are of the form `part1s-*.scala`.
    * [tapf](../fragments/part1s-tapf.scala)
    * [cat](../fragments/part1s-cat.scala)
    * [adt](../fragments/part1s-adt.scala)
    * [mon](../fragments/part1s-mon.scala)
    * [assoc](../fragments/part1s-assoc.scala)
    * [tc](../fragments/part1s-tc.scala)

### 3. Some scala exercises


### 4. 



#### eof

