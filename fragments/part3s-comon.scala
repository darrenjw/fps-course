// Chunk:  mdoc
import cats.Functor
trait Comonad[F[_]] extends Functor[F]:
  extension [A](fa: F[A])
    def extract: A
  extension [A, B](fa: F[A])
    def coflatMap(f: F[A] => B): F[B]
// End chunk

// Chunk: 
extension [A](fa: F[A])
  def coflatten: F[F[A]]
// End chunk

// Chunk:  mdoc
import cats.*
import cats.implicits.*

def s(lam: Double = 3.7) = LazyList.iterate(0.5)(
  x => lam*x*(1-x))
  
s().take(5).toList

def linearFilter(weights: LazyList[Double])(
  s: LazyList[Double]): Double =
    (weights, s).parMapN(_*_).sum

def filt = linearFilter(LazyList(0.25,0.5,0.25)) _
// End chunk

// Chunk:  mdoc
filt(s())

s().coflatMap(filt).take(5).toList
// End chunk

// Chunk:  mdoc
case class Store[K, V](peek: K => V, cursor: K):
  def extract: V = peek(cursor)
  def coflatMap[B](fs: Store[K, V] => B): Store[K, B] =
    Store(k => fs(Store(peek, k)), cursor)

  def coflatten: Store[K, Store[K, V]] =
    coflatMap(identity)
  def map[B](f: V => B): Store[K, B] =
    coflatMap(s => f(s.extract))

  def seek(k: K): Store[K,V] = Store(peek, k)
// End chunk

// Chunk:  mdoc
val s = Store(Map(1->"Alice", 2->"Bob", 3->"Charles"), 1)
s.extract
val s2 = s.seek(2)
s2.extract
// End chunk

