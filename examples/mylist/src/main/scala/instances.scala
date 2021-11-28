/*
instances.scala

Cats instances for MyList

 */


object Instances:

  import cats.*
  import cats.implicits.*

  // MonoidK instance
  // This is how to define a monoid instance for a parameterised type:
  // Uses <+> rather than |+| for combine syntax
  given MonoidK[MyList] with
    def combineK[A](a1: MyList[A], a2: MyList[A]): MyList[A] = a1 ++ a2
    def empty[A]: MyList[A] = MyNil

  // Functor instance
  given Functor[MyList] with
    def map[A, B](fa: MyList[A])(f: A => B): MyList[B] = fa.map(f)

  // CoflatMap instance
  given CoflatMap[MyList] with
    def map[A, B](fa: MyList[A])(f: A => B): MyList[B] = fa.map(f)
    def coflatMap[A, B](fa: MyList[A])(f: MyList[A] => B): MyList[B] = fa.coflatMap(f)

  // Monad instance
  given Monad[MyList] with
    def pure[A](a: A): MyList[A] = a :: MyNil
    def flatMap[A,B](fa: MyList[A])(f: A => MyList[B]): MyList[B] = fa.flatMap(f)
    def tailRecM[A, B](a: A)(f: A => MyList[Either[A,B]]): MyList[B] = ???

  // Foldable instance
  given Foldable[MyList] with
    def foldLeft[A, B](fa: MyList[A], b: B)(f: (B, A) => B): B = fa.foldLeft(b)(f)
    def foldRight[A, B](fa: MyList[A],lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.foldRight(lb)((a: A, eb: Eval[B]) => f(a, eb))





// eof

