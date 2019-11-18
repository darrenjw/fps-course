/*
mylist.scala

An immutable linked list data type from scratch
*/

trait MyList[+A] {

  def show: String = this match {
    case MyNil        => "#"
    case MyCons(a, l) => a.toString + " :: " + l.show
  }

  def ::[A](a: A) = MyCons(a, this)

//  def map[B](f: A => B): List[B] = this match {
//    case MyNil        => (MyNil: List[B])
//    case MyCons(a, l) => f(a) ::[B] l.map(f)
//  }

}

case object MyNil extends MyList[Nothing]

case class MyCons[+A](a: A, l: MyList[A]) extends MyList[A]

object MyList {

  def apply[A](a: A): MyList[A] = pure(a)

  def pure[A](a: A): MyList[A] = MyCons(a, MyNil)

}

object MyListApp {



  def main(args: Array[String]): Unit = {
    val l = 1 :: 2 :: 3 :: MyNil
    println(l.show)
    println(MyList.pure(2).show)
    println(("a" :: "b" :: "c" :: MyNil).show)
  }

}
