// Chunk:  mdoc
val sqrt: Double => Option[Double] = x =>
  if (x < 0) None else Some(math.sqrt(x))

sqrt(16.0) map (sqrt(_))
// End chunk

// Chunk:  mdoc
sqrt(16.0).map{sqrt(_)}.flatten
sqrt(16.0).flatMap{sqrt(_)}
for
  x <- sqrt(16)
  y <- sqrt(x)
yield y

import cats.*
import cats.implicits.*
sqrt(16.0) >>= sqrt
// End chunk

// Chunk: 
  def pure[A](x: A): F[A]
  extension [A](ffa: F[F[A]])
    def flatten: F[A]
// End chunk

// Chunk: 
  extension [A, B](fa: F[A]) def flatMap(f: A => F[B]): F[B]
// End chunk

// Chunk: 
def flatMapAssociativity[A, B, C](
  fa: F[A], f: A => F[B], g: B => F[C]) =
    fa.flatMap(f).flatMap(g) <-> 
	  fa.flatMap(a => f(a).flatMap(g))

def monadLeftIdentity[A, B](a: A, f: A => F[B]) =
  F.pure(a).flatMap(f) <-> f(a)

def monadRightIdentity[A](fa: F[A]) =
  fa.flatMap(F.pure) <-> fa
// End chunk

// Chunk:  mdoc
def fourthRoot[M[_]: Monad](md: M[Double])(
  sqrtM: Double => M[Double]): M[Double] = for
    x <- md
    y <- sqrtM(x)
    z <- sqrtM(y)
  yield z

fourthRoot(Option(16.0))(sqrt)
// End chunk

// Chunk: 
extension [A, B](a: A)
  def tailRecM(f: A => F[Either[A, B]]): F[B] = ???
// End chunk

// Chunk: 
def tailRecM[A, B](a: A)(f: A => F[Either[A, B]]): F[B] =
    flatMap(f(a)) {
      case Right(b) => pure(b)
      case Left(nextA) => tailRecM(nextA)(f)
    }
// End chunk

// Chunk: 
final case class Kleisli[F[_], A, B](run: A => F[B])
// End chunk

// Chunk:  mdoc
import cats.data.Kleisli
val sqrtk = Kleisli(sqrt)
val fourthk = sqrtk compose sqrtk
fourthk.run(16.0)
// End chunk

// Chunk:  mdoc
val xr = 0 until 2
val yr = 0 until 3
xr.flatMap(x => {yr.map(y => (x,y))})
// End chunk

// Chunk:  mdoc
for
  x <- xr
  y <- yr
yield (x,y)
// End chunk

// Chunk:  mdoc
val log: Double => Option[Double] = x => if (x > 0)
  Some(math.log(x)) else None
val loglog: Double => Option[Double] = x => for
  lx <- log(x)
  llx <- log(lx)
yield llx
loglog(2.0)
loglog(0.5)
// End chunk

// Chunk:  mdoc
val sqrtE: Double => Either[String, Double] = x =>
  if (x < 0) Left("Attempt to square root a negative number")
    else Right(math.sqrt(x))

sqrtE(4.0)
sqrtE(-1.0)
sqrtE(16.0) >>= sqrtE
sqrtE(-1.0) >>= sqrtE

fourthRoot(16.0.asRight[String])(sqrtE)
// End chunk

// Chunk:  mdoc
Right[String, Int](1)
Left[String, Int]("Hi")

Either.right[String, Int](1)
Either.left[String, Int]("Hi")

1.asRight[String]
"Hi".asLeft[Int]
// End chunk

// Chunk:  mdoc
fourthRoot(Option(16.0))(sqrt)
// End chunk

// Chunk: 
fourthRoot(16.0)(math.sqrt _)
// End chunk

// Chunk:  mdoc
val sqrtId: Double => Id[Double] = 
  x => Monad[Id].pure(math.sqrt(x))

fourthRoot(Monad[Id].pure(16.0))(sqrtId)
// End chunk

// Chunk:  mdoc
case class Conf(width: Int, height: Int)
case class Pos(x: Int, y: Int)

def neighbours(conf: Conf, p: Pos): List[Pos] = List(
  Pos(p.x-1, p.y), Pos(p.x+1, p.y), Pos(p.x, p.y+1), 
  Pos(p.x, p.y-1)).filter(isValid(conf, _))

def isValid(conf: Conf, p: Pos): Boolean = ((p.x >= 0) &&
  (p.y >= 0) && (p.x < conf.width) && (p.y < conf.height))

val conf = Conf(20,15)
neighbours(conf, Pos(5, 0))
// End chunk

// Chunk:  mdoc
import cats.data.Reader
type ConfReader[A] = Reader[Conf, A]

def neighboursR(p: Pos): ConfReader[List[Pos]] = Reader(
  conf => List(Pos(p.x-1, p.y), Pos(p.x+1, p.y), 
  Pos(p.x, p.y+1), Pos(p.x, p.y-1)).filter(isValidR(_).
  run(conf)))

def isValidR(p: Pos): ConfReader[Boolean] = Reader(conf =>
  ((p.x >= 0) && (p.y >= 0) && (p.x < conf.width) && 
    (p.y < conf.height)))

neighboursR(Pos(5, 0)).run(conf)
// End chunk

// Chunk:  mdoc
import cats.data.Writer

Writer(Vector("Logged value"), 4)
5.writer(Vector("Another logged value"))

type Log[V] = Writer[Vector[String], V]

val loggedValue = for
  a <- 5.pure[Log]
  _ <- Vector("log").tell
  b <- 3.writer(Vector("logged value of 3"))
  _ <- Vector("more log").tell
yield (a + b)

loggedValue.value
loggedValue.written
loggedValue.run
// End chunk

// Chunk:  mdoc
import cats.data.State
type IDState[A] = State[Int, A]
def createID(): IDState[String] = State(counter => 
  (counter+1, "user"+counter))
  
val ids = for
  u1 <- createID()
  u2 <- createID()
yield List(("Angela", u1), ("Barry", u2))

val people = List("Andrew", "Betty", "Charles", "Doris")
val moreIds = people.traverse(
  p => createID().map(id => (p, id)) )

val all = for
  l1 <- ids
  l2 <- moreIds
yield l1 ++ l2

all.runA(1).value
// End chunk

// Chunk:  mdoc
import scala.util.{Try, Success, Failure}

def checkFile(filename: String): String = 
  Try(scala.io.Source.fromFile(filename)) match
    case Success(file) => "File: " + filename + " exists!"
    case Failure(err)  => "Whoops! " + err
  
checkFile("Readme.md")
checkFile("README.md")
// End chunk

// Chunk:  mdoc:reset-class
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.duration.*
import scala.util.{Try, Success, Failure}
import ExecutionContext.Implicits.global

val f1 = Future {
  Thread.sleep(1000)
  1 }
val f2 = Future {
  Thread.sleep(1000)
  2 }
val f3 = for
  v1 <- f1
  v2 <- f2
yield (v1+v2)
f3.onComplete( _ match
  case Success(v) => "Result: " + v
  case _          => "Whoops!" )
scala.concurrent.Await.result(f3, 5.second)  
// End chunk

