// Chunk:  mdoc:silent
import cats._
import cats.implicits._

val listOptionFunctor = Functor[List].compose[Option]
// End chunk

// Chunk:  mdoc
val lo = List(1,2,3) map (i => i.some)
val lo2 = listOptionFunctor.map(lo)(i => i*2)
// End chunk

// Chunk:  mdoc
import cats.data.Nested
val nested: Nested[List, Option, Int] = Nested(lo)
nested.map(i => i*2).value
// End chunk

// Chunk:  mdoc
val composed = Applicative[List].compose[Option].map2(
  lo, lo2)(_ + _)

val nested2 = Applicative[Nested[List, Option, *]].map2(
  Nested(lo), Nested(lo2))(_ + _)
// End chunk

// Chunk:  mdoc
import cats.data.OptionT
val lom = OptionT(lo)
lom.map(i => i*2)
lom.flatMap(i => OptionT(List(i.some,(i+1).some)))
// End chunk

