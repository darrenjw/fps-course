// Chunk:  mdoc
import cats.*
import cats.implicits.*

Monoid[Int].empty
Monoid[Int].combine(2,3)
4.combine(5)
6 |+| 7
List(1,2) |+| List(3,4,5)
"Hi" |+| "There"
// End chunk

// Chunk:  mdoc
def combineAll[A: Monoid](as: List[A]): A =
  as.foldLeft(Monoid[A].empty)(_ |+| _)

combineAll(List(1, 2, 3))
combineAll(List("a", "bc", "de"))
combineAll(List(Map('a' -> 1), Map('a' -> 2, 'b' -> 3), 
  Map('b' -> 4, 'c' -> 5)))
// End chunk

// Chunk:  mdoc
val s = "The quick brown fox jumped over the lazy dogs."

s.toList.map(c => Map(c -> 1)).reduce(_ |+| _).toList.
  sortBy(-_._2).take(5)
// End chunk

