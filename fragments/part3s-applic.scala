// Chunk:  mdoc
import cats.Functor
trait Applicative[F[_]] extends Functor[F]:
  def pure[A](a: A): F[A]
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
// End chunk

// Chunk:  mdoc:reset
import cats.*
import cats.implicits.*

(1.some).product(2.some)
(1.some) product (2.some)
(1.some, 2.some).tupled
Applicative[Option].map2(1.some, 2.some)(_+_)
(1.some, 2.some, 3.some).tupled
Applicative[Option].map3(1.some, 2.some, 3.some)(_+_+_)
(1.some, 2.some, 3.some).mapN(_+_+_)
// End chunk

// Chunk:  mdoc
trait Applicative[F[_]] extends Functor[F]:
  def pure[A](a: A): F[A]
  def ap[A, B](fab: F[A => B])(fa: F[A]): F[B]
// End chunk

// Chunk: 
def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    ap(map(fa)(a => (b: B) => (a, b)))(fb)
// End chunk

// Chunk:  mdoc
val inc: Int => Int = i => i+1
(inc.some).ap(4.some)
inc.some ap 4.some
inc.some <*> 4.some
inc.some <*> None
(None: Option[Int => Int]) <*> 4.some
(None: Option[Int => Int]) <*> None
// End chunk

// Chunk:  mdoc:reset-class
import cats.*
import cats.implicits.*
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}
import ExecutionContext.Implicits.global

val f1: () => Future[Int] = () => Future {
  Thread.sleep(1000)
  1 }

f1().onComplete(i => println("hi"))

val f2 = for
  v1 <- f1()
  v2 <- f1()
yield (v1+v2)
f2.onComplete(i => println("hi"))

(f1(), f1()).mapN(_+_).onComplete(println(_))

Thread.sleep(3000)
// End chunk

// Chunk:  mdoc
val l1 = List(1,2,3)
val l2 = List(4,5,6)
for
  i <- l1
  j <- l2
yield (i+j)
// End chunk

// Chunk:  mdoc
Monad[List].product(l1,l2)
// End chunk

// Chunk:  mdoc
Applicative[List].product(l1,l2)
// End chunk

// Chunk:  mdoc
Parallel.parProduct(l1,l2)
// End chunk

// Chunk:  mdoc
(l1, l2).mapN(_ + _)
(l1, l2).parMapN(_ + _)
// End chunk

// Chunk:  mdoc
import cats.data.Validated
import cats.data.Validated.*
Valid(3)
Validated.valid[String, Int](3)
3.valid
3.valid[String]
Invalid("Error")
Validated.invalid[String, Int]("Error")
"Error".invalid
"Error".invalid[Int]
// End chunk

// Chunk:  mdoc
val v1 = 1.valid[Vector[String]]
val v2 = 2.valid[Vector[String]]
val i1 = Vector("error1").invalid[Int]
val i2 = Vector("error2").invalid[Int]
(v1, v2).mapN(_+_)
(v1, i2).mapN(_+_)
(i1, v2).mapN(_+_)
(i1, i2).mapN(_+_)
(i1, i2, i2).mapN(_+_+_)
// End chunk

// Chunk:  mdoc
trait Foldable[F[_]]:
  extension [A, B](fa: F[A])
    def foldLeft(b: B)(f: (B, A) => B): B
  extension [A, B](fa: F[A])
    def foldRight(b: B)(f: (A, B) => B): B
// End chunk

// Chunk: 
extension [A, B: Monoid](fa: F[A])
  def foldMap(f: A => B): B
extension [G[_]: Monad, A, B: Monoid](fa: F[A])
  def foldMapM(f: A => G[B]): G[B]
// End chunk

// Chunk:  mdoc
List(1,2,3).foldLeft(0)(_+_)
List(1,2,3).foldRight(0)(_+_)
List(1,2,3).foldLeft(0)(_-_)
List(1,2,3).foldRight(0)(_-_)
List(1,2,3).foldMap(_.toDouble)
List(1,2,3).foldMap(_.toDouble.some)
List(1,2,3).foldMap(i => List(i))
List(1,2,3).foldMapM(i => List(i))
// End chunk

// Chunk:  mdoc
trait Traverse[F[_]] extends Functor[F] with Foldable[F]:
  extension [G[_]: Applicative, A, B](fa: F[A])
    def traverse(f: A => G[B]): G[F[B]]
// End chunk

// Chunk: 
extension [G[_]: Applicative, A](fga: F[G[A]])
  def sequence: G[F[A]]
// End chunk

// Chunk:  mdoc
val list = (1 to 5).toList
list.map(i => Option(i)).sequence
list.traverse(i => Option(i))
// End chunk

