// Chunk: 
import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

class MyPropTests extends ScalaCheckSuite:
// End chunk

// Chunk: 
  property("math.sqrt should be a square root") {
    forAll { (d: Double) =>
      if d >= 0.0 then
        val s = math.sqrt(d)
        // squaring the square root should get back to the input
        assert(math.abs(s*s - d) <= 1.0e-8*d) // tolerance on result
      else
        assert(true) // pass negative values
    }
  }
// End chunk

// Chunk: 
  property("An Int should combine commutatively") {
    forAll { (a: Int, b: Int) =>
      assertEquals(a |+| b, b |+| a)
    }
  }

  property("An Int should invert") {
    forAll { (a: Int) =>
      assertEquals(a |+| a.inverse(), Monoid[Int].empty)
    }
  }
// End chunk

