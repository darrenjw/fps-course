// Chunk:  mdoc
Vector(1,2,3).sum
List(1.0,5.0).sum
// End chunk

// Chunk:  mdoc:fail
Vector(1,2,3).mean
// End chunk

// Chunk:  mdoc
extension [T: Numeric] (it: Iterable[T])
  def mean = (it map (summon[Numeric[T]].toDouble)).sum/it.size
// End chunk

// Chunk:  mdoc
Vector(1,2,3).mean
List(1.0,3.0,5.0,7.0).mean
// End chunk

// Chunk:  mdoc
trait CsvRow[T]:
  extension (t: T) def toCsv: String
// End chunk

// Chunk:  mdoc
def printRows[T: CsvRow](it: Iterable[T]): Unit =
  it.foreach(row => println(row.toCsv))
// End chunk

// Chunk:  mdoc
case class MyState(x: Int, y: Double)
// End chunk

// Chunk:  mdoc
given CsvRow[MyState] with
  extension (row: MyState)
    def toCsv = row.x.toString + "," + row.y
// End chunk

// Chunk:  mdoc
MyState(1, 2.0).toCsv
printRows(List(MyState(1, 2.0),MyState(2, 3.0)))
// End chunk

// Chunk:  mdoc
given CsvRow[Vector[Double]] with
  extension (row: Vector[Double])
    def toCsv = row.mkString(",")

Vector(1.0,2.0,3.0).toCsv
printRows(List(Vector(1.0,2.0),Vector(4.0,5.0),
  Vector(3.0,3.0)))
// End chunk

// Chunk:  mdoc
trait Thinnable[F[_]]:
  extension [T](ft: F[T])
    def thin(th: Int): F[T]
// End chunk

// Chunk:  mdoc
given Thinnable[LazyList] with
  extension [T](s: LazyList[T])
    def thin(th: Int): LazyList[T] =
      val ss = s.drop(th-1)
      if (ss.isEmpty) LazyList.empty else
        ss.head #:: ss.tail.thin(th)
// End chunk

// Chunk:  mdoc
LazyList.iterate(0)(_ + 1).drop(10).thin(2).
  take(5).toArray
// End chunk

// Chunk:  mdoc
case class IntDouble(i: Int, d: Double)
IntDouble(2, 1.4)
// End chunk

// Chunk:  mdoc
import cats.*
import cats.implicits.*

given Monoid[IntDouble] with
  def empty = IntDouble(0, 0.0)
  def combine(a: IntDouble, b: IntDouble) = 
    IntDouble(a.i + b.i, a.d + b.d)

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

