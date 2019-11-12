# Part 1: ADTs and typeclasses

## Practical exercises

These exercises are to be undertaken following the presentation of the material from Part 1 of the course.

### 1. Setup

* Make sure that you are set up to write, compile and run code using the libraries and versions used in this course. I have a [sbt giter8 project template](https://github.com/darrenjw/fps.g8) that should make this very easy. Assuming that you have [sbt]() installed, you should just be able to do:
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

Note that if for some reason you can't or don't want to use `sbt new`, there is an [example blank template](../app-template) available for you to copy, edit and import. Note that if you are using an IDE like IntelliJ, you should at least edit the project name in `build.sbt` before importing, as IntelliJ gets confused if you try to import multiple projects with the same name.

### 2. Paste fragments


### 3. Some scala exercises


### 4. 



#### eof

