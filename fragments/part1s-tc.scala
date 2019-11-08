// Chunk:  mdoc
Vector(1,2,3).sum
List(1.0,5.0).sum
// End chunk

// Chunk:  mdoc:fail
Vector(1,2,3).mean
// End chunk

// Chunk:  mdoc
object Meanable {
  def mean[T: Numeric](it: Iterable[T]): Double =
    it.map(implicitly[Numeric[T]].toDouble(_)).
      sum / it.size
}
// End chunk

// Chunk:  mdoc
object Meanable2 {
  def mean[T](it: Iterable[T])(
    implicit num: Numeric[T]): Double =
      it.map(num.toDouble(_)).sum / it.size
}
// End chunk

// Chunk:  mdoc
import Meanable._
mean(Vector(1,2,3))
mean(List(1.0,5.0))
// End chunk

// Chunk:  mdoc
implicit class MeanableInstance[T: Numeric](
  it: Iterable[T]) {
    def mean[T] = Meanable.mean(it)
}
// End chunk

// Chunk:  mdoc
Vector(1,2,3).mean
List(1.0,3.0,5.0,7.0).mean
// End chunk

// Chunk:  mdoc
trait CsvRow[T] {
  def toCsv(row: T): String
}
// End chunk

// Chunk:  mdoc
implicit class CsvRowSyntax[T](row: T) {
  def toCsv(implicit inst: CsvRow[T]) = inst.toCsv(row)
}
// End chunk

// Chunk:  mdoc
def printRows[T: CsvRow](it: Iterable[T]): Unit =
  it.foreach(row => println(row.toCsv))
// End chunk

// Chunk:  mdoc
case class MyState(x: Int, y: Double)
// End chunk

// Chunk:  mdoc
implicit val myStateCsvRow = new CsvRow[MyState] {
  def toCsv(row: MyState) = row.x.toString+","+row.y
}
// End chunk

// Chunk:  mdoc
MyState(1,2.0).toCsv
printRows(List(MyState(1,2.0),MyState(2,3.0)))
// End chunk

// Chunk:  mdoc
implicit val vectorDoubleCsvRow =
  new CsvRow[Vector[Double]] {
    def toCsv(row: Vector[Double]) = row.mkString(",")
  }

Vector(1.0,2.0,3.0).toCsv
printRows(List(Vector(1.0,2.0),Vector(4.0,5.0),
  Vector(3.0,3.0)))
// End chunk

// Chunk:  mdoc
trait Thinnable[F[_]] {
  def thin[T](f: F[T], th: Int): F[T]
}
// End chunk

// Chunk:  mdoc
implicit class ThinnableSyntax[T,F[_]](value: F[T]) {
  def thin(th: Int)(implicit inst: Thinnable[F]): F[T] =
    inst.thin(value,th)
}
// End chunk

// Chunk:  mdoc
implicit val streamThinnable: Thinnable[Stream] =
  new Thinnable[Stream] {
    def thin[T](s: Stream[T],th: Int): Stream[T] = {
      val ss = s.drop(th-1)
      if (ss.isEmpty) Stream.empty else
        ss.head #:: thin(ss.tail, th)
    }
}
// End chunk

// Chunk:  mdoc
Stream.iterate(0)(_ + 1).drop(10).thin(2).
  take(5).toArray
// End chunk

// Chunk:  mdoc:reset
import simulacrum._

@typeclass
trait Thinnable[F[_]] {
  def thin[T](f: F[T], th: Int): F[T]
}

import Thinnable.ops._
// End chunk

// Chunk:  mdoc
implicit val streamThinnable: Thinnable[Stream] =
  new Thinnable[Stream] {
    def thin[T](s: Stream[T],th: Int): Stream[T] = {
      val ss = s.drop(th-1)
      if (ss.isEmpty) Stream.empty else
        ss.head #:: thin(ss.tail, th)
    }
}
// End chunk

// Chunk:  mdoc
Stream.iterate(0)(_ + 1).drop(10).thin(2).
  take(5).toArray
// End chunk

// Chunk:  mdoc
case class IntDouble(i: Int, d: Double)
IntDouble(2, 1.4)
// End chunk

// Chunk:  mdoc
import cats._
import cats.implicits._

implicit val intDoubleMonoid: Monoid[IntDouble] =
  new Monoid[IntDouble] {
    def empty = IntDouble(0, 0.0)
	def combine(a: IntDouble, b: IntDouble) = 
	  IntDouble(a.i + b.i, a.d + b.d)
  }

IntDouble(2, 1.4) |+| IntDouble(3, 2.1)
// End chunk

// Chunk: 
trait Monoid[A] {
  def empty: A
  def combine(x: A, y: A): A
}
// End chunk

// Chunk:  mdoc
def combineAll[A: Monoid](as: List[A]): A =
  as.foldLeft(Monoid[A].empty)(_ |+| _)

combineAll(List(IntDouble(2, 1.4), IntDouble(3, 2.1),
  IntDouble(4, 3.5)))
// End chunk

