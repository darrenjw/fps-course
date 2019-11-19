import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class MyListSpec extends AnyFlatSpec with Matchers {

val smallList = MyList.intList(1,15)

 "A MyList" should "combine" in {
   val l = (1 :: 2 :: MyNil) ++ (3 :: 4 :: MyNil)
   l shouldBe (1 :: 2 :: 3 :: 4 :: MyNil)
  }

  it should "combine sequentially" in {
    MyList.intList(1,10) ++ MyList.intList(11,20) shouldBe MyList.intList(1,20)
  }

  it should "reverse" in {
    (1 :: 2 :: 3 :: MyNil).reverse shouldBe (3 :: 2 :: 1 :: MyNil)
  }

  it should "reverse-reverse" in {
    smallList.reverse.reverse shouldBe smallList
  }

  it should "map" in {
    (1 :: 2 :: 3 :: MyNil) map (_*2) shouldBe 2 :: 4 :: 6 :: MyNil
  }

  it should "flatMap" in {
    (1 :: 2 :: 3 :: MyNil) flatMap (a => (a :: a*2 :: MyNil)) shouldBe (
      1 :: 2 :: 2 :: 4 :: 3 :: 6 :: MyNil)
  }

  it should "coflatMap" in {
    (2 :: 5 :: MyNil) coflatMap(_.foldLeft(0)(_+_)) shouldBe (7 :: 5 :: MyNil)
  }


  // Some more based on examples from "main"


}


// Example property-based tests
import org.scalatestplus.scalacheck._
class MyListPropTests extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {

  "An Int" should "combine commutatively" in {
    forAll { (a: Int, b: Int) =>
      (a + b) should be (b + a)
    }
  }





}



// eof


