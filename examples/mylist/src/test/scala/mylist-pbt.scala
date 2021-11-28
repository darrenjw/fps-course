// Example property-based tests

import munit.ScalaCheckSuite
import org.scalacheck.*
import org.scalacheck.Prop.*

import cats.*
import cats.implicits.*

class MyListPropTests extends ScalaCheckSuite:

  // This should automatically define arbitrary instances (but doesn't seem to work)
  //import org.scalacheck.ScalacheckShapeless._

  // Start with manual building of lists
  val smallInt = Gen.choose(0, 100)

  property("A MyList should size correctly") {
    forAll(smallInt) { (n) =>
        assertEquals(MyList.intList(n).size, n)
    }
  }

  // Gen for MyList[T], where T is Arbitrary
  def genNil[T: Arbitrary]: Gen[MyList[T]] = Gen.const(MyNil: MyList[T])
  def genCons[T: Arbitrary]: Gen[MyList[T]] = for {
    h <- Arbitrary.arbitrary[T]
    t <- genList[T]
  } yield MyCons(h, t)
  // def genList[T: Arbitrary]: Gen[MyList[T]] = Gen.oneOf(genNil[T], genCons[T])
  // bias to longer lists...
  def genList[T: Arbitrary]: Gen[MyList[T]] = Gen.oneOf(genNil[T], genCons[T], genCons[T], genCons[T], genCons[T])

  // Can use the generator explicity to generate random instances
  property("A MyList[Int] should reverse.reverse") {
    forAll(genList[Int]) { (l) =>
      assertEquals(l.reverse.reverse, l)
    }
  }

  property("A MyList[String] should reverse.reverse") {
    forAll(genList[String]) { (l) =>
      assertEquals(l.reverse.reverse, l)
    }
  }

  property("A MyList[Double] should reverse.reverse") {
    forAll(genList[Double]) { (l) =>
      assertEquals(l.reverse.reverse, l)
    }
  }

  // Create an Arbitrary instance for MyList[T]
  given [T: Arbitrary]: Arbitrary[MyList[T]] = Arbitrary(genList[T])

  // Can use the Arbitrary instance to implicitly generate random instances
  property("An arbitrary MyList[String] should reverse.reverse") {
    forAll { (l: MyList[String]) =>
      assertEquals(l.reverse.reverse, l)
    }
  }

  property("An arbitrary MyList[Int] should reverse.reverse") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.reverse.reverse, l)
    }
  }

  property("it should maintain size after mapping") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.map(_*2).size, l.size)
    }
  }

  property("it should have size = sizeUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.size, l.sizeUnsafe)
    }
  }

  property("it should have foldLeft = foldRightUnsafe for associative operation") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.foldLeft(0)(_+_), l.foldRightUnsafe(0)(_+_))
    }
  }

  import cats.Eval
  property("it should have foldRight = foldRightUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.foldRight(Eval.always(0))((a, lb) => lb.map(a - _)).value,
        l.foldRightUnsafe(0)(_ - _))
    }
  }

  property("it should have map = mapUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.map(_*2), l.mapUnsafe(_*2))
    }
  }

  property("it should have reverse = reverseUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.reverse, l.reverseUnsafe)
    }
  }

  property("it should have concat = concatUnsafe") {
    forAll { (l1: MyList[Int], l2: MyList[Int]) =>
      assertEquals(l1.concat(l2), l1.concatUnsafe(l2))
    }
  }

  property("it should have flatMap = flatMapUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.flatMap(i => i :: i+1 :: i*2 :: MyNil),
        l.flatMapUnsafe(i => i :: i+1 :: i*2 :: MyNil))
    }
  }

  property("it should have coflatMap = coflatMapUnsafe") {
    forAll { (l: MyList[Int]) =>
      assertEquals(l.coflatMap(_.foldLeft(0)(_+_)), l.coflatMapUnsafe(_.foldLeft(0)(_+_)))
    }
  }

  // TODO: More PBTs here...


// eof


