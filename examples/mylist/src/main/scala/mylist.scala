/*
mylist.scala

An immutable linked list data type from scratch
*/

// Base trait
trait MyList[+A] {

  def show: String = this match {
    case MyNil        => "#"
    case MyCons(a, l) => a.toString + " :: " + l.show
  }

  //def ::[B <: A](a: B): MyList[A] = MyCons(a, this)

  def map[B](f: A => B): MyList[B] = this match {
    case MyNil        => MyNil
    case MyCons(a, l) => MyCons(f(a), l.map(f))
  }

  // do foldLeft next...

}

// Nil object
case object MyNil extends MyList[Nothing]

// Cons data type
case class MyCons[+A](a: A, l: MyList[A]) extends MyList[A]

// Companion object
object MyList {

  def apply[A](a: A): MyList[A] = pure(a)

  def pure[A](a: A): MyList[A] = MyCons(a, MyNil)

}



object MyListApp {

  def main(args: Array[String]): Unit = {
    //val l = 1 :: 2 :: 3 :: MyNil
    val l = MyCons(1, MyCons(2, MyCons(3, MyNil)))
    println(l.show)
    println(MyList.pure(2).show)
    //println(("a" :: "b" :: "c" :: MyNil).show)
    println((MyCons("a", MyCons("b", MyCons("c", MyNil)))).show)
    println((l map (_*2)).show)
  }

}
