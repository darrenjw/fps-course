# Part 2: Functors and monads

## Practical exercises

### 1. Code fragments

* Go through the [code fragments](../fragments/) for Part 2:

    * [func](../fragments/part2s-func.scala)
    * [monad](../fragments/part2s-monad.scala)

Paste the chunks one at a time and experiment in the REPL if you are unsure what is going on.

### 2. Scala exercises

* Go back to the [FP in Scala exercises](https://www.scala-exercises.org/fp_in_scala/handling_error_without_exceptions) and have a go at some of those. Don't worry if you get stuck - material is covered in a different order to this course - just leave it and come back to it later.
* Go back to the [Cats exercises](https://www.scala-exercises.org/cats/functor) and do "Functor", "Monad", "Identity" and "Either". Again, don't worry if you don't understand everything - we are doing things in a slightly different order.

### 3. Writer monad

* Returning to the `LeavingPresent` ADT from the exercises for Part 1, create a function with signature
```scala
def addContribution(lp: LeavingPresent)(name: String, Amount: Double): LeavingPresent
```
which does the obvious thing. Use this to write a function:
```scala
def addContributions(lp: LeavingPresent)(names: List[String], amounts: List[Double]): LeavingPresent
```
* Now refactor the code by making the return types `Writer[List[String], LeavingPresent]`, which logs the names and amounts (as a `String`) of all the contributions that have been added to the `LeavingPresent`.
* Now refactor `addContributions` so that it will work for any monadic context. That is, for a `M[LeavingPresent]` for any `Monad[M]`.

I realise this is a very artificial example, but it's tricky to come up with examples that are both realistic and simple!


#### eof

