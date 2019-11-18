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

  // def foldRight[B](lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B]

  def reverse: MyList[A] = foldLeft(MyNil: MyList[A]){ case (b, a) => a :: b }

  def mapUnsafe[B](f: A => B): MyList[B] = this match {
    case MyNil        => MyNil
    case MyCons(a, l) => f(a) :: l.mapUnsafe(f)
  }

  def map[B](f: A => B): MyList[B] = this.reverse.
    foldLeft(MyNil: MyList[B]){ case (b, a) => f(a) :: b }

  def concatUnsafe[B >: A](lb: MyList[B]): MyList[B] = this match {
    case MyNil => lb
    case MyCons(a, la) => a :: la.concatUnsafe(lb)
  }

  def concat[B >: A](lb: MyList[B]): MyList[B] = this.reverse.
    foldLeft(lb){ case (b, a) => a :: b }

  def ++[B >: A](lb: MyList[B]): MyList[B] = this.concat(lb)

  def flatMap[B](f: A => MyList[B]): MyList[B] = this.reverse.
    foldLeft(MyNil: MyList[B]){ case (b, a) => f(a) ++ b }

  def coflatMap[B](f: MyList[A] => B): MyList[B] = ???

  // scanLeft (safe and unsafe?)

  // foldMap

  // traverse

  // tailRecM

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
// * Cats instances (including Foldable)
// * Test with cats-laws


// Application object
object MyListApp {

  def main(args: Array[String]): Unit = {
    val l = 1 :: 2 :: 3 :: MyNil
    println(l)
    println(MyList.pure(2))
    println(("a" :: "b" :: "c" :: MyNil))
    println(l map (_*2))
    println(l flatMap(a => a :: 2*a :: MyNil))
    println(l mapUnsafe (_*2))
    println(l map (_.toDouble))
    println(l.foldLeft(0)(_+_))
    println(l.foldRight(0)(_+_))
    println(l.foldLeft(0)(_-_))
    println(l.foldRight(0)(_-_))
    println(MyList.intList(4) concatUnsafe MyList.intList(6))
    println(MyList.intList(4) concat MyList.intList(6))
    println(MyList.intList(4) ++ MyList.intList(6))
    println(MyList.intList(6).reverse)

    val smallList = MyList.intList(10)
    println(smallList)
    val medList = MyList.intListUnsafe(1000)
    val bigList = MyList.intList(100000)
    println(bigList.foldLeft(0)(_+_))
    val mapped = bigList map (_*2)
    
    bigList concat bigList


  }

}

// eof
