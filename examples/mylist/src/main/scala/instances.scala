/*
instances.scala

Cats instances for MyList

 */


object Instances {

  import cats._
  import cats.implicits._

  // MonoidK instance
  // This is how to define a monoid instance for a parameterised type:
  // Uses <+> rather than |+| for combine syntax
  implicit val monoidMyList: MonoidK[MyList] = new MonoidK[MyList] {
    def combineK[A](a1: MyList[A], a2: MyList[A]): MyList[A] = a1 ++ a2
    def empty[A]: MyList[A] = MyNil
  }

  // Functor instance
  implicit val functorMyList: Functor[MyList] = new Functor[MyList] {
    def map[A, B](fa: MyList[A])(f: A => B): MyList[B] = fa.map(f)
  }

  // CoflatMap instance
  implicit val coflatMapMyList: CoflatMap[MyList] = new CoflatMap[MyList] {
    def map[A, B](fa: MyList[A])(f: A => B): MyList[B] = fa.map(f)
    def coflatMap[A, B](fa: MyList[A])(f: MyList[A] => B): MyList[B] = fa.coflatMap(f)
  }

  // Foldable instance
  implicit val foldableMyList: Foldable[MyList] = new Foldable[MyList] {
    def foldLeft[A, B](fa: MyList[A], b: B)(f: (B, A) => B): B = fa.foldLeft(b)(f)
    def foldRight[A, B](fa: MyList[A],lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.foldRight(lb)((a: A, eb: Eval[B]) => f(a, eb))
  }




}

// eof

