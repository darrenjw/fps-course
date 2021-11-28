// Example unit tests

import munit.*

class MyListSpec extends FunSuite:

  val smallList = MyList.intList(15)

  test("A MyList should combine") {
    val l = (1 :: 2 :: MyNil) ++ (3 :: 4 :: MyNil)
    assertEquals(l, 1 :: 2 :: 3 :: 4 :: MyNil)
  }

  test("it should combine sequentially") {
    assertEquals(MyList.intList(10) ++ MyList.intList(20,11), MyList.intList(20))
  }

  test("it should reverse") {
    assertEquals((1 :: 2 :: 3 :: MyNil).reverse, (3 :: 2 :: 1 :: MyNil))
  }

  test("it should reverse-reverse") {
    assertEquals(smallList.reverse.reverse, smallList)
  }

  test("it should size correctly") {
    assertEquals(smallList.size, 15)
  }

  test("it should map") {
    assertEquals((1 :: 2 :: 3 :: MyNil) map (_*2), 2 :: 4 :: 6 :: MyNil)
  }

  test("it should foldLeft") {
    assertEquals((1 :: 2 :: 3 :: MyNil).foldLeft(0)(_+_), 6)
  }

  test("it should foldRightUnsafe") {
    assertEquals((1 :: 2 :: 3 :: MyNil).foldRightUnsafe(0)(_+_), 6)
  }

  test("it should foldLeft (non-assoc)") {
    assertEquals((1 :: 2 :: 3 :: MyNil).foldLeft(0)(_-_), -6)
  }

  test("it should foldRight (non-assoc)") {
    assertEquals((1 :: 2 :: 3 :: MyNil).foldRightUnsafe(0)(_-_), 2)
  }

  test("it should flatMap") {
    assertEquals((1 :: 2 :: 3 :: MyNil) flatMap (a => (a :: a*2 :: MyNil)),
      (1 :: 2 :: 2 :: 4 :: 3 :: 6 :: MyNil))
  }

  test("it should coflatMap") {
    assertEquals((2 :: 5 :: MyNil) coflatMap(_.foldLeft(0)(_+_)), (7 :: 5 :: MyNil))
  }

  test("it should coflatten") {
    assertEquals((2 :: 5 :: MyNil).coflatten, (2 :: 5 :: MyNil) :: (5 :: MyNil) :: MyNil)
  }

  test("it should coflatten.flatten") {
    assertEquals((2 :: 5 :: MyNil).coflatten.flatten, 2 :: 5 :: 5 :: MyNil)
  }

  test("it should flatten a flat list") {
    assertEquals(smallList.flatten, smallList)
  }



// eof


