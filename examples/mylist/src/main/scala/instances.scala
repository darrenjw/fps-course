/*
instances.scala

Cats instances for MyList

 */


object Instances {

  import cats._
  import cats.implicits._

  // This is how to define a monoid instance for a parameterised type:
  // Uses <+> rather than |+| for combine syntax
  implicit val monoidMyList: MonoidK[MyList] = new MonoidK[MyList] {
    def combineK[A](a1: MyList[A], a2: MyList[A]): MyList[A] = a1 ++ a2
    def empty[A]: MyList[A] = MyNil
  }

}

// eof

