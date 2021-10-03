// Example property-based tests

import org.scalatest.flatspec.{AnyFlatSpec}
import org.scalatest.matchers.should.Matchers

import org.scalatestplus.scalacheck._
import org.scalacheck._
import Gen._
import Arbitrary.arbitrary

import cats._
import cats.implicits._
import org.scalatest.funsuite.AnyFunSuite
import org.typelevel.discipline.scalatest.Discipline
import org.scalacheck.ScalacheckShapeless._

// Actual Cats laws can be found here:
// https://github.com/typelevel/cats/tree/master/laws/src/main/scala/cats/laws
// Associated discipline tests are in the subdirectory:
// https://github.com/typelevel/cats/tree/master/laws/src/main/scala/cats/laws/discipline


class MyListLawTests extends AnyFunSuite with Discipline {

  // TODO: Generators and Arbitrary currently _copied_ from PBTs - should factor out
  // Gen for MyList[T], where T is Arbitrary
  def genNil[T: Arbitrary] = const(MyNil: MyList[T])
  def genCons[T: Arbitrary] = for {
    h <- arbitrary[T]
    t <- genList[T]
  } yield MyCons(h, t)
  def genList[T: Arbitrary]: Gen[MyList[T]] = Gen.oneOf(genNil[T], lzy(genCons[T]), lzy(genCons[T]), lzy(genCons[T]), lzy(genCons[T]))
  // Create an Arbitrary instance for MyList[T]
  implicit def arbMyList[T: Arbitrary]: Arbitrary[MyList[T]] = Arbitrary(genList[T])
  // Need an equality for MyList
  implicit def eqMyList[T: Eq]: Eq[MyList[T]] = Eq.fromUniversalEquals

  // Monoid
  import Instances.monoidMyList
  import cats.laws.discipline.MonoidKTests
  // check laws for MyList[Int] instances
  checkAll("MyList.MonoidKLaws", MonoidKTests[MyList].monoidK[Int])
  // now check again for MyList[String] instances
  checkAll("MyList.MonoidKLaws (String)", MonoidKTests[MyList].monoidK[String])

  // Functor
  import Instances.functorMyList
  import cats.laws.discipline.FunctorTests
  checkAll("MyList.FunctorLaws", FunctorTests[MyList].functor[Int, Int, String])
  checkAll("MyList.FunctorLaws (SID)", FunctorTests[MyList].functor[String, Int, Double])
  // Three types, since covariantComposition law is parameterised by three arbitrary types

  // CoflatMap
  import Instances.coflatMapMyList
  import cats.laws.discipline.CoflatMapTests
  implicit def cogenMyList[A: Cogen]: Cogen[MyList[A]] = {
    implicitly[Cogen[List[A]]].contramap(_.toList)
  }
  checkAll("MyList.CoflatMapLaws", CoflatMapTests[MyList].coflatMap[Int, Int, String])

  // Monad
  import Instances.monadMyList
  import cats.laws.discipline.MonadTests
  import cats.laws.discipline.SemigroupalTests.Isomorphisms
  implicit val myListIsomorphisms: Isomorphisms[MyList] =
    Isomorphisms.invariant(monadMyList)
  checkAll("MyList.MonadLaws", MonadTests[MyList].monad[Int, Int, String])

  // Foldable
  import Instances.foldableMyList
  import cats.laws.discipline.FoldableTests
  checkAll("MyList.FoldableLaws", FoldableTests[MyList].foldable[Int, Int])
  checkAll("MyList.FoldableLaws (DI)", FoldableTests[MyList].foldable[Double, Int])


}


// eof


