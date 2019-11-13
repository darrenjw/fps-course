// Chunk: 
import org.scalatestplus.scalacheck._

class MyPropertyTests extends AnyFlatSpec with 
  Matchers with ScalaCheckPropertyChecks {
// End chunk

// Chunk: 
  "math.sqrt" should "be a square root" in {
    forAll { (d: Double) =>
      whenever (d >= 0.0) {
        val s = math.sqrt(d)
        (s*s) shouldBe (d +- 1.0e-8*d)
      }
    }
  }
// End chunk

// Chunk: 
  "An Int" should "combine commutatively" in {
    forAll { (a: Int, b: Int) =>
      (a |+| b) should be (b |+| a)
    }
  }

  it should "invert" in {
    forAll { (a: Int) =>
      (a |+| a.inverse) shouldBe Monoid[Int].empty
    }
  }
// End chunk

