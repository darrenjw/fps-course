// Example property-based tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.scalatestplus.scalacheck._
import org.scalacheck._
import Gen._
import Arbitrary.arbitrary

class MyListPropTests extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {

  // This might be able to automatically define arbitrary instances (but doesn't seem to work)
  //import org.scalacheck.ScalacheckShapeless._

  // Start with manual building of lists
  val smallInt = Gen.choose(0, 100)

  "A MyList" should "size correctly" in {
    forAll(smallInt) { (n) =>
        MyList.intList(n).size shouldBe n
    }
  }

  // Now create a generator for random instances of MyList[Int]
  val genNil = const(MyNil: MyList[Int])
  val genIntCons = for {
    h <- arbitrary[Int]
    t <- genIntList
  } yield MyCons(h, t)
  //def genIntList: Gen[MyList[Int]] = Gen.oneOf(genNil, lzy(genIntCons))
  // bias towards cons for longer lists:
  def genIntList: Gen[MyList[Int]] = Gen.oneOf(genNil, lzy(genIntCons), lzy(genIntCons), lzy(genIntCons), lzy(genIntCons))

  // Can use the generator explicity to generate random instances
  "A MyList[Int]" should "reverse.reverse" in {
    forAll(genIntList) { (l) =>
      l.reverse.reverse shouldBe l
    }
  }

  // Create an Arbitrary instance for MyList[Int]
  implicit lazy val arbIntList: Arbitrary[MyList[Int]] = Arbitrary(genIntList)

  // Can use the Arbitrary instance to implicitly generate random instances
  "An arbitrary MyList[Int]" should "reverse.reverse" in {
    forAll { (l: MyList[Int]) =>
      l.reverse.reverse shouldBe l
    }
  }

  it should "maintain size after mapping" in {
    forAll { (l: MyList[Int]) =>
      l.map(_*2).size shouldBe l.size
    }
  }

  it should "have size = sizeUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.size shouldBe l.sizeUnsafe
    }
  }

  it should "have foldLeft = foldRight for associative operation" in {
    forAll { (l: MyList[Int]) =>
      l.foldLeft(0)(_+_) shouldBe l.foldRight(0)(_+_)
    }
  }

  it should "have map = mapUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.map(_*2) shouldBe l.mapUnsafe(_*2)
    }
  }

  it should "have reverse = reverseUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.reverse shouldBe l.reverseUnsafe
    }
  }

  it should "have concat = concatUnsafe" in {
    forAll { (l1: MyList[Int], l2: MyList[Int]) =>
      l1.concat(l2) shouldBe l1.concatUnsafe(l2)
    }
  }

  it should "have flatMap = flatMapUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.flatMap(i => i :: i+1 :: i*2 :: MyNil) shouldBe l.flatMapUnsafe(i => i :: i+1 :: i*2 :: MyNil)
    }
  }

  it should "have coflatMap = coflatMapUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.coflatMap(_.foldLeft(0)(_+_)) shouldBe l.coflatMapUnsafe(_.foldLeft(0)(_+_))
    }
  }



}



// eof


