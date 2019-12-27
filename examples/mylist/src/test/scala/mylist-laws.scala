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

class MyListLawTests extends AnyFunSuite with Discipline {

  import Instances._

  // Create a generator for random instances of MyList[Int] (copied from above - should factor out)
  val genNil = const(MyNil: MyList[Int])
  val genIntCons = for {
    h <- arbitrary[Int]
    t <- genIntList
  } yield MyCons(h, t)
  //def genIntList: Gen[MyList[Int]] = Gen.oneOf(genNil, lzy(genIntCons))
  // bias towards cons for longer lists:
  def genIntList: Gen[MyList[Int]] = Gen.oneOf(genNil, lzy(genIntCons), lzy(genIntCons), lzy(genIntCons), lzy(genIntCons))
  // Create an Arbitrary instance for MyList[Int]
  implicit lazy val arbIntList: Arbitrary[MyList[Int]] = Arbitrary(genIntList)
  // Need an equality for Tree
  implicit def eqMyList[A: Eq]: Eq[MyList[A]] = Eq.fromUniversalEquals


  import cats.laws.discipline.MonoidKTests
  checkAll("MyList.MonoidKLaws", MonoidKTests[MyList].monoidK[Int])
  import cats.laws.discipline.FunctorTests
  checkAll("MyList.FunctorLaws", FunctorTests[MyList].functor[Int, Int, String])
  import cats.laws.discipline.CoflatMapTests
  // TODO: Needs a Cogen instance
  //checkAll("MyList.CoflatMapLaws", CoflatMapTests[MyList].coflatMap[Int, Int, String])
  import cats.laws.discipline.FoldableTests
  checkAll("MyList.FoldableLaws", FoldableTests[MyList].foldable[Int, Int])


}


// eof


