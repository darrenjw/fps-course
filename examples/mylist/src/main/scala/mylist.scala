/*
mylist.scala
An immutable linked list data type from scratch
*/

import annotation.tailrec

// Base trait
trait MyList[+A] {

  // Not stack safe, but shouldn't use on big lists anyway
  override def toString: String = this match {
    case MyNil        => "MyNil"
    case MyCons(a, l) => a.toString + " :: " + l.toString
  }

  // Cons operator
  def ::[B >: A](a: B): MyList[B] = MyCons(a, this)

  // UNSAFE
  def map[B](f: A => B): MyList[B] = this match {
    case MyNil        => MyNil
    case MyCons(a, l) => f(a) :: l.map(f)
  }

  @tailrec
  final def foldLeft[B](b: B)(f: (B, A) => B): B = this match {
    case MyNil => b
    case MyCons(a, l) => l.foldLeft(f(b, a))(f)
  }

  // concat (++ or ::: ?)

  // reverse

  // foldRight (unsafe)

  // scanLeft (safe and unsafe?)

  // safe map

  // flatMap (safe and unsafe?)

  // traverse

  // coflatMap

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
// * Cats instances
// * Test with cats-laws


// Application object
object MyListApp {

  def main(args: Array[String]): Unit = {
    val l = 1 :: 2 :: 3 :: MyNil
    println(l)
    println(MyList.pure(2))
    println(("a" :: "b" :: "c" :: MyNil))
    println(l map (_*2))
    println(l map (_.toDouble))
    println(l.foldLeft(0)(_+_))
    val smallList = MyList.intList(10)
    println(smallList)
    val medList = MyList.intListUnsafe(1000)
    val bigList = MyList.intList(100000)
    println(bigList.foldLeft(0)(_+_))
    val mapped = medList map (_*2)
  }

}

// eof
