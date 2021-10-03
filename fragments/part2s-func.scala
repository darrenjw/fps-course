// Chunk:  mdoc
List(1,2,3).map(_*2)
// End chunk

// Chunk: 
trait Functor[F[_]]:
  extension [A,B](fa: F[A])
    def map(f: A => B): F[B]
// End chunk

// Chunk:  mdoc
def reverseAll(ls: List[String]) = ls.map(_.reverse)

reverseAll(List("hi","there"))
// End chunk

// Chunk:  mdoc
import cats.*
import cats.implicits.*

def reverseAllF[F[_]: Functor](fs: F[String]) = 
  fs.map(_.reverse)

reverseAllF(List("hi","there"))
reverseAllF(Vector("The","quick","brown","fox"))
reverseAllF(Option("maybe"))
// End chunk

// Chunk: 
         a.map(f compose g) == a.map(g).map(f)
// End chunk

// Chunk: 
       alpha(fa.map(f)) = (alpha(fa)).map(f)
// End chunk

// Chunk:  mdoc
def option2list[A](o: Option[A]): List[A] = o match
  case Some(a) => List(a)
  case None => Nil

option2list(Option(3))
option2list(Option("hello"))
option2list(None)
// End chunk

// Chunk:  mdoc:silent
val o2l = [A] => (o: Option[A]) => o match
  case Some(a) => List(a)
  case None => Nil
// End chunk

// Chunk:  mdoc
o2l(Some("poly"))
o2l(Option(2))
o2l(None)
// End chunk

// Chunk: 
trait FunctionK[F[_], G[_]]:
  def apply[A](fa: F[A]): G[A]
// End chunk

// Chunk:  mdoc
import cats.arrow.FunctionK

val o2lFk = new FunctionK[Option, List]:
  def apply[A](o: Option[A]): List[A] = o match
    case Some(a) => List(a)
    case None => Nil

o2lFk(Option(3))
o2lFk(Option("hello"))
o2lFk(None)
// End chunk

// Chunk:  mdoc
val o2lFk2 = new (Option ~> List):
  def apply[A](o: Option[A]): List[A] = o match
    case Some(a) => List(a)
    case None => Nil

o2lFk2(Option(3))
// End chunk

// Chunk: 
  extension [A, B](fa: F[A]) def contramap(f: B => A): F[B]
// End chunk

// Chunk:  mdoc
case class Dog(name: String, age: Int)
val d2s: Dog => String = d => d.name.show + " is " +
  d.age.show + " years old"
given Show[Dog] = Show[String].contramap(d2s)
Dog("fido",5).show
// End chunk

// Chunk:  mdoc
trait Bifunctor[F[_,_]]:
  extension [A, B, C, D](fab: F[A, B])
    def bimap(f: A => C, g: B => D): F[C, D]
// End chunk

// Chunk:  mdoc
(1, "hello").bimap(_.toDouble, _.capitalize)
// End chunk

// Chunk:  mdoc
trait Profunctor[F[_,_]]:
  extension [A,B,C,D](fab: F[A, B])
    def dimap(f: C => A)(g: B => D): F[C, D]
// End chunk

// Chunk:  mdoc
import cats.arrow.Profunctor

val cap: String => String = s => s.capitalize

cap("hello")
// End chunk

// Chunk:  mdoc
case class Person(name: String)

val capPerson = cap.dimap((p: Person) => p.name)(
  s => Person(s))
  
capPerson(Person("fred"))
// End chunk

