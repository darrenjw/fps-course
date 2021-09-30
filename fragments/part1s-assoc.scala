// Chunk:  mdoc:invisible
import cats.*
import cats.implicits.*
// End chunk

// Chunk:  mdoc
import cats.data.*

val l=NonEmptyList(2,List(3,4))
val m=NonEmptyList(5,List(6))
l |+| m
// End chunk

// Chunk:  mdoc
val ls = List("The","quick","brown","fox")
ls.map(_ |+| " ").fold(Monoid[String].empty)(_ |+| _)
// End chunk

// Chunk:  mdoc
Group[Int].empty
4 + 4.inverse()
// End chunk

// Chunk:  mdoc
import cats.kernel.CommutativeGroup
CommutativeGroup[Int].empty
(4 |+| 7) |+| (7 |+| 4).inverse()
// End chunk

