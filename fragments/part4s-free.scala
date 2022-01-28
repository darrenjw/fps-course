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

// Chunk:  mdoc
sum.fold("0")("(" + _.toString + " + " + _.toString + ")")
// End chunk

// Chunk:  mdoc
case class Coyo[F[_], A, B](fa: F[A], f: A => B):

  def map[C](fbc: B => C): Coyo[F, A, C] =
    Coyo(fa, (fbc compose f))

  def fold(fun: Functor[F]): F[B] = fun.map(fa)(f)

def lift[F[_], A](fa: F[A]): Coyo[F, A, A] = Coyo(fa, identity)
// End chunk

// Chunk:  mdoc
// a parameterised type, with no functor instance
case class Mytype[A](hidden: A)

// lift and then map at will
val x = lift(Mytype("hello")).map(_.length).map(_ * 2).map(_.toDouble)

// provide a functor instance to get a value back
val funct = new Functor[Mytype]:
  def map[A, B](fa: Mytype[A])(f: A => B): Mytype[B] = fa match
    case Mytype(x) => Mytype(f(x))

// use the functor to fold back into a value
val y = x.fold(funct)
// End chunk

// Chunk:  mdoc
import cats.free.*
val z = Coyoneda.lift(Mytype("hello")).map(_.length).map(_ * 2)
val zz = z.run(funct) 
// just use run() if a "given" functor instance is in scope
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
type WrappedF[A] = Free[Wrapped, A]
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

// Chunk:  mdoc
import cats.data.State
type IDState[A] = State[Int, A]
def createID(): IDState[String] = State(counter =>
  (counter+1, "user"+counter))

val people = List("Andrew", "Betty", "Charles", "Doris")
val prog = people.traverse(
  p => createID().map(id => (p, id)) )
val ids = prog.runA(1).value
// End chunk

// Chunk:  mdoc:reset
enum IDState[A]:
  case CreateID() extends IDState[String]
import IDState.*
// End chunk

// Chunk:  mdoc:invisible
import cats.*
import cats.implicits.*
// End chunk

// Chunk:  mdoc
import cats.free.Free
type IDStateF[A] = Free[IDState,A]

import cats.free.Free.liftF
def createID(): IDStateF[String] =
  liftF[IDState, String](CreateID())
// End chunk

// Chunk:  mdoc
val people = List("Andrew", "Betty", "Charles", "Doris")
val prog = people.traverse(
  p => createID().map(id => (p, id)) )
// End chunk

// Chunk:  mdoc
import cats.data.State
type IDStateM[A] = State[Int, A]
val csm = new (IDState ~> IDStateM):
  def apply[A](fa: IDState[A]): IDStateM[A] = fa match
    case CreateID() => 
      State(counter => (counter+1, "user"+counter))
// End chunk

// Chunk:  mdoc
val compiled = prog.foldMap(csm)
val ids = compiled.runA(1).value
// End chunk

// Chunk:  mdoc:reset:invisible
import cats.*
import cats.implicits.*
// End chunk

// Chunk:  mdoc
trait IDState[F[_]]:
  def createID(): F[String]
// End chunk

// Chunk:  mdoc
def prog[F[_]: Monad](ids: IDState[F]) =
  import ids.*  
  val people = List("Andrew", "Betty", "Charles", "Doris")
  people.traverse(
	p => createID().map(id => (p, id)) )
// End chunk

// Chunk:  mdoc
import cats.data.State
type IDStateM[A] = State[Int, A]
object IDStateC extends IDState[IDStateM]:
  def createID(): IDStateM[String] = State(counter =>
    (counter+1, "user"+counter))
// End chunk

// Chunk:  mdoc
val compiled = prog(IDStateC)
val ids = compiled.runA(1).value
// End chunk

