// Chunk: 
import org.scalatestplus.scalacheck.*

class MyPropTests extends propspec.AnyPropSpec
  with ScalaCheckPropertyChecks
  with TableDrivenPropertyChecks with should.Matchers:
// End chunk

// Chunk: 
  property("math.sqrt should be a square root") {
    forAll { (d: Double) =>
      whenever (d >= 0.0) {
        val s = math.sqrt(d)
        (s*s) should be (d +- 1.0e-8*d)
      }
    }
  }
// End chunk

// Chunk: 
  property("An Int should combine commutatively") {
    forAll { (a: Int, b: Int) =>
      (a |+| b) should be (b |+| a)
    }
  }

  property("An Int should invert") {
    forAll { (a: Int) =>
      (a |+| a.inverse()) shouldBe Monoid[Int].empty
    }
  }
// End chunk

