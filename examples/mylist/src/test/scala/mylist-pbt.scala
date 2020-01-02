// Example property-based tests

import org.scalatest.flatspec.{AnyFlatSpec}
import org.scalatest.matchers.should.Matchers

import org.scalatestplus.scalacheck._
import org.scalacheck._
import Gen._
import Arbitrary.arbitrary

class MyListPropTests extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {

  // This should automatically define arbitrary instances (but doesn't seem to work)
  //import org.scalacheck.ScalacheckShapeless._

  // Start with manual building of lists
  val smallInt = Gen.choose(0, 100)

  "A MyList" should "size correctly" in {
    forAll(smallInt) { (n) =>
        MyList.intList(n).size shouldBe n
    }
  }

  // Gen for MyList[T], where T is Arbitrary
  def genNil[T: Arbitrary] = const(MyNil: MyList[T])
  def genCons[T: Arbitrary] = for {
    h <- arbitrary[T]
    t <- genList[T]
  } yield MyCons(h, t)
  // def genList[T: Arbitrary]: Gen[MyList[T]] = Gen.oneOf(genNil[T], lzy(genCons[T]))
  // bias to longer lists...
  def genList[T: Arbitrary]: Gen[MyList[T]] = Gen.oneOf(genNil[T], lzy(genCons[T]), lzy(genCons[T]), lzy(genCons[T]), lzy(genCons[T]))

  // Can use the generator explicity to generate random instances
  "A MyList[Int]" should "reverse.reverse" in {
    forAll(genList[Int]) { (l) =>
      l.reverse.reverse shouldBe l
    }
  }

  "A MyList[String]" should "reverse.reverse" in {
    forAll(genList[String]) { (l) =>
      l.reverse.reverse shouldBe l
    }
  }

  "A MyList[Double]" should "reverse.reverse" in {
    forAll(genList[Double]) { (l) =>
      l.reverse.reverse shouldBe l
    }
  }

  // Create an Arbitrary instance for MyList[T]
  implicit def arbMyList[T: Arbitrary]: Arbitrary[MyList[T]] = Arbitrary(genList[T])

  // Can use the Arbitrary instance to implicitly generate random instances
  "An arbitrary MyList[String]" should "reverse.reverse" in {
    forAll { (l: MyList[String]) =>
      l.reverse.reverse shouldBe l
    }
  }

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

  it should "have foldLeft = foldRightUnsafe for associative operation" in {
    forAll { (l: MyList[Int]) =>
      l.foldLeft(0)(_+_) shouldBe l.foldRightUnsafe(0)(_+_)
    }
  }

  import cats.Eval
  it should "have foldRight = foldRightUnsafe" in {
    forAll { (l: MyList[Int]) =>
      l.foldRight(Eval.always(0))((a, lb) => lb.map(a - _)).value shouldBe l.foldRightUnsafe(0)(_ - _)
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

  // TODO: More PBTs here...

}



// eof


