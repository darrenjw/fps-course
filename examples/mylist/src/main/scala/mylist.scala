/*
mylist.scala
An immutable linked list data type from scratch
*/

import annotation.tailrec

// Base trait
trait MyList[+A] {

  // Not stack safe, but shouldn't use on big lists, anyway
  override def toString: String = this match {
    case MyNil        => "MyNil"
    case MyCons(a, l) => a.toString + " :: " + l.toString
  }

  def ::[B >: A](a: B): MyList[B] = MyCons(a, this)

  @tailrec
  final def foldLeft[B](b: B)(f: (B, A) => B): B = this match {
    case MyNil => b
    case MyCons(a, l) => l.foldLeft(f(b, a))(f)
  }

  // Not stack-safe but requires some kind of trampolining to fix
  def foldRight[B](b: B)(f: (A, B) => B): B = this match {
    case MyNil => b
    case MyCons(a, l) => f(a, l.foldRight(b)(f))
  }

  // Cats type signature:
  // def foldRight[B](lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B]

  def reverseUnsafe: MyList[A] = this match {
    case MyNil => MyNil
    case MyCons(a, la) => la.reverseUnsafe.concatUnsafe(MyList.pure(a))
  }

  def reverse: MyList[A] = foldLeft(MyNil: MyList[A]){ case (b, a) => a :: b }

  def sizeUnsafe: Int = this match {
    case MyNil         => 0
    case MyCons(a, la) => 1 + la.sizeUnsafe
  }

  def size: Int = foldLeft(0){ case (b, a) => b + 1 }

  def mapUnsafe[B](f: A => B): MyList[B] = this match {
    case MyNil        => MyNil
    case MyCons(a, l) => f(a) :: l.mapUnsafe(f)
  }

  def map[B](f: A => B): MyList[B] = reverse.
    foldLeft(MyNil: MyList[B]){ case (b, a) => f(a) :: b }

  def concatUnsafe[B >: A](lb: MyList[B]): MyList[B] = this match {
    case MyNil => lb
    case MyCons(a, la) => a :: la.concatUnsafe(lb)
  }

  def concat[B >: A](lb: MyList[B]): MyList[B] = reverse.
    foldLeft(lb){ case (b, a) => a :: b }

  def ++[B >: A](lb: MyList[B]): MyList[B] = concat(lb)

  def flatMapUnsafe[B](f: A => MyList[B]): MyList[B] = this match {
    case MyNil         => MyNil
    case MyCons(a, la) => f(a) ++ la.flatMapUnsafe(f)
  }

  def flatMap[B](f: A => MyList[B]): MyList[B] = reverse.
    foldLeft(MyNil: MyList[B]){ case (b, a) => f(a) ++ b }

  def flatten = flatMap(_ match {
    case MyCons(a, la) => MyCons(a, la)
    case a => MyList.pure(a)
  })

  def coflatMapUnsafe[B](f: MyList[A] => B): MyList[B] = this match {
    case MyNil => MyNil
    case MyCons(a, la) => f(MyCons(a, la)) :: la.coflatMapUnsafe(f)
  }

  def coflatMap[B](f: MyList[A] => B): MyList[B] = reverse.
    foldLeft((MyNil, MyNil): (MyList[A], MyList[B])){ case (lalb, a) =>
      lalb match { case (la, lb) =>
        (a :: la, f(a :: la) :: lb)
      }}._2

  def coflatten = coflatMap(identity)

  // filter

  // scanLeft

  // foldMap

  // traverse

  // tailRecM

  // drop

  // take

  // dropWhile

  // takeWhile

  // headOption

  // tailOption

  // applyOption?!

}

// Nil object
case object MyNil extends MyList[Nothing]

// Cons data type
case class MyCons[A](a: A, l: MyList[A]) extends MyList[A]

// Companion object
object MyList {

  def apply[A](a: A): MyList[A] = pure(a)

  def pure[A](a: A): MyList[A] = a :: MyNil

  def intListUnsafe(to: Int, from: Int = 1): MyList[Int] =
    if (from > to) MyNil
    else from :: intListUnsafe(to, from + 1)

  @tailrec
  def intList(to: Int, from: Int = 1, acc: MyList[Int] = MyNil): MyList[Int] =
    if (from > to) acc
    else intList(to-1, from, to :: acc)

}

// TODO:
// * Unit tests
// * Property based tests
// * Cats instances (including Monad, CoFlatMap, Foldable and Traverse)
// * Test with cats-laws


// Application object
object MyListApp {

  def main(args: Array[String]): Unit = {
    val l = 1 :: 2 :: 3 :: MyNil
    println(l)
    println(l.reverseUnsafe)
    println(l.reverse)
    println(MyList.pure(2))
    println(("a" :: "b" :: "c" :: MyNil))
    println(l map (_*2))
    println(l mapUnsafe (_*2))
    println(l map (_.toDouble))
    println(l.foldLeft(0)(_+_))
    println(l.foldRight(0)(_+_))
    println(l.foldLeft(0)(_-_))
    println(l.foldRight(0)(_-_))
    println(l flatMapUnsafe (a => a :: 2*a :: MyNil))
    println(l flatMap (a => a :: 2*a :: MyNil))
    println(l coflatMapUnsafe (_.foldLeft(0)(_+_)))
    println(l coflatMap (_.foldLeft(0)(_+_)))
    println(l.coflatten)
    println(l.coflatten.flatten)
    println(MyList.intList(4) concatUnsafe MyList.intList(6))
    println(MyList.intList(4) concat MyList.intList(6))
    println(MyList.intList(4) ++ MyList.intList(6))
    println(MyList.intList(6).reverse)
    val smallList = MyList.intList(10)
    val medList = MyList.intListUnsafe(1000)
    val bigList = MyList.intList(100000)
    println(smallList)
    println(bigList.foldLeft(0)(_+_))
    val mapped = bigList map (_*2)
    medList.coflatten.flatten
    medList coflatMap (_.foldLeft(0)(_+_))
    println(medList.sizeUnsafe)
    println(bigList.size)
  }

}

// eof
