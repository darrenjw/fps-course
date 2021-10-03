// Chunk:  mdoc
val a = {println("A"); 1}
a
a
lazy val b = {println("B"); 2}
b
b
def c = {println("C"); 3}
c
c
// End chunk

// Chunk:  mdoc
def fun(a: Int, b: => Int): Int =
  println("fun side-effect")
  a*b

fun(2, 3)
fun( {println("A side-effect"); 2},
  {println("B side-effect"); 3} )
// End chunk

// Chunk:  mdoc
val d = new Function0[Int]:
  def apply() = { println("D"); 4 }
d
d()
d()
// End chunk

// Chunk:  mdoc
import cats.*
import cats.implicits.*
import cats.data.Reader
type Thunk[A] = Reader[Unit, A]
val e: Thunk[Int] = Reader(u => {println("E"); 6})
e
e(())
e.run(())
// End chunk

// Chunk:  mdoc
val f = Eval.now{ println("F"); 7}
f
f.value
f.value
// End chunk

// Chunk:  mdoc
val g = Eval.later{ println("G"); 8}
g
g.value
g.value

val h = Eval.always{ println("H"); 9}
h
h.value
h.value
// End chunk

// Chunk: 
extension [A, B](fa: F[A])
  def foldRight (lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B]
// End chunk

// Chunk:  mdoc
val sum = List(2) |+| List(3) |+| List(4)
// End chunk

// Chunk:  mdoc
sum.fold(0)(_+_)
sum.fold(1)(_*_)
// End chunk

// Chunk: 
sealed abstract class Free[F[_], A]
case class Pure[F[_], A](a: A) extends Free[F, A]
case class Suspend[F[_], A](
  a: F[Free[F, A]]) extends Free[F, A]
// End chunk

// Chunk:  mdoc
import cats.free.*
case class Wrapped[A](a: A)
type WrappedF[A] = Free[Wrapped,A]
val p3 = Monad[WrappedF].pure(3)
val comp = p3.flatMap(i => Monad[WrappedF].pure(i*2))
comp
// End chunk

// Chunk:  mdoc
val w2v = new (Wrapped ~> Id):
  def apply[A](w: Wrapped[A]): A = w match
    case Wrapped(a) => a
// End chunk

// Chunk: 
def foldMap[M[_]](f: FunctionK[S,M])(M: Monad[M]): M[A]
// End chunk

// Chunk:  mdoc
comp.foldMap(w2v)
// End chunk

// Chunk:  mdoc
val w2e = new (Wrapped ~> Eval) {
  def apply[A](w: Wrapped[A]): Eval[A] = w match {
    case Wrapped(a) => Eval.later(a)
  }
}

val ce = comp.foldMap(w2e)
ce.value
// End chunk

// Chunk:  mdoc
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.duration.*
//import scala.util.{Try, Success, Failure}
import ExecutionContext.Implicits.global

val w2f = new (Wrapped ~> Future):
  def apply[A](w: Wrapped[A]): Future[A] = w match
    case Wrapped(a) => Future{ a }

val cf = comp.foldMap(w2f)
scala.concurrent.Await.result(cf, 1.second)
// End chunk

