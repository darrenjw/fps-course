// Chunk:  mdoc
val aLongString = (1 to 10000).map(_.toString).
                               reduce(_+_)

val stringLength: String => Int = s => s.length

stringLength(aLongString)
// End chunk

// Chunk:  mdoc
def convertToK: Int => Double = i => i.toDouble/1024

def stringLengthInK1(s: String): Double =
  val l = stringLength(s)
  val lk = convertToK(l)
  lk

stringLengthInK1(aLongString)
// End chunk

// Chunk:  mdoc
val stringLengthInK2: String => Double =
                      s => convertToK(stringLength(s))

stringLengthInK2(aLongString)
// End chunk

// Chunk:  mdoc
val stringLengthInK3: String => Double =
             s => (convertToK compose stringLength)(s)

stringLengthInK3(aLongString)
// End chunk

// Chunk:  mdoc
val stringLengthInK4: String => Double =
                      convertToK compose stringLength

stringLengthInK4(aLongString)
// End chunk

// Chunk:  mdoc
def myLength[T](l: List[T]): Int = l match
  case Nil => 0
  case x :: xs => 1 + myLength(xs)

myLength(List(1, 2, 3))
// End chunk

// Chunk: 
myLength(List(1, 2, 3))
= 1 + myLength(List(2, 3)) 
= 1 + 1 + myLength(List(3))
= 1 + 1 + 1 + myLength(Nil)
= 1 + 1 + 1 + 0
= 3
// End chunk

// Chunk:  mdoc
val f : Int => Double = x => x.toDouble
f(3)
// End chunk

// Chunk:  mdoc
val f1 = new Function1[Int, Double]:
  def apply(x: Int) = x.toDouble

f1(5)
// End chunk

// Chunk:  mdoc
val sr = new PartialFunction[Double, Double]:
  def apply(x: Double) = math.sqrt(x)
  def isDefinedAt(x: Double) = x >= 0

sr.isDefinedAt(2.0)
sr(2.0)
sr.isDefinedAt(-1.0)
// End chunk

// Chunk:  mdoc
enum Pet:
  case Cat(age: Int)
  case Dog(age: Int)

import Pet.*

val dogYears: PartialFunction[Pet,Int] =
  case Dog(a) => a*7
dogYears.isDefinedAt(Dog(3))
dogYears(Dog(3))
dogYears.isDefinedAt(Cat(3))
// End chunk

// Chunk:  mdoc
val catYears: PartialFunction[Pet,Int] =
  case Cat(a) => a*6
catYears.isDefinedAt(Dog(3))
catYears.isDefinedAt(Cat(3))
catYears(Cat(3))
// End chunk

// Chunk:  mdoc
val petYears = dogYears orElse catYears
petYears.isDefinedAt(Dog(3))
petYears(Dog(3))
petYears.isDefinedAt(Cat(3))
petYears(Cat(3))
// End chunk

