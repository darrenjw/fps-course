import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class MyListSpec extends AnyFlatSpec with Matchers {

  val smallList = MyList.intList(15)

 "A MyList" should "combine" in {
   val l = (1 :: 2 :: MyNil) ++ (3 :: 4 :: MyNil)
   l shouldBe (1 :: 2 :: 3 :: 4 :: MyNil)
  }

  it should "combine sequentially" in {
    MyList.intList(10) ++ MyList.intList(20,11) shouldBe MyList.intList(20)
  }

  it should "reverse" in {
    (1 :: 2 :: 3 :: MyNil).reverse shouldBe (3 :: 2 :: 1 :: MyNil)
  }

  it should "reverse-reverse" in {
    smallList.reverse.reverse shouldBe smallList
  }

  it should "size correctly" in {
    smallList.size shouldBe 15
  }

  it should "map" in {
    (1 :: 2 :: 3 :: MyNil) map (_*2) shouldBe 2 :: 4 :: 6 :: MyNil
  }

  it should "foldLeft" in {
    (1 :: 2 :: 3 :: MyNil).foldLeft(0)(_+_) shouldBe 6
  }

  it should "foldRight" in {
    (1 :: 2 :: 3 :: MyNil).foldRight(0)(_+_) shouldBe 6
  }

  it should "foldLeft (non-assoc)" in {
    (1 :: 2 :: 3 :: MyNil).foldLeft(0)(_-_) shouldBe -6
  }

  it should "foldRight (non-assoc)" in {
    (1 :: 2 :: 3 :: MyNil).foldRight(0)(_-_) shouldBe 2
  }

  it should "flatMap" in {
    (1 :: 2 :: 3 :: MyNil) flatMap (a => (a :: a*2 :: MyNil)) shouldBe (
      1 :: 2 :: 2 :: 4 :: 3 :: 6 :: MyNil)
  }

  it should "coflatMap" in {
    (2 :: 5 :: MyNil) coflatMap(_.foldLeft(0)(_+_)) shouldBe (7 :: 5 :: MyNil)
  }

  it should "coflatten" in {
    (2 :: 5 :: MyNil).coflatten shouldBe (2 :: 5 :: MyNil) :: (5 :: MyNil) :: MyNil
  }

  it should "coflatten.flatten" in {
    (2 :: 5 :: MyNil).coflatten.flatten shouldBe 2 :: 5 :: 5 :: MyNil
  }

  it should "flatten a flat list" in {
    smallList.flatten shouldBe smallList
  }


}


// Example property-based tests
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
  def genIntList: Gen[MyList[Int]] = Gen.oneOf(genNil, lzy(genIntCons))

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
      //println(l)
      l.reverse.reverse shouldBe l
    }
  }






  /*
  "A MyList" should "size correctly" in {
    forAll { (n: Int) =>
      whenever ((n >= 0) && (n < 10000)) {
        MyList.intList(n).size shouldBe n
      }
    }
  }
   */



}



// eof


