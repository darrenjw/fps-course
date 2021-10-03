// Chunk:  mdoc
val a = 3
val b = false
val p = (a, b)
p._1
p._2
p(0)
p(1)
// End chunk

// Chunk:  mdoc
case class IntBool(i: Int, b: Boolean)
val pcc = IntBool(3, false)
pcc.i
// End chunk

// Chunk:  mdoc
val tup2cc: ((Int, Boolean)) => IntBool = tup =>
  IntBool(tup._1, tup._2)
val cc2tup: IntBool => (Int, Boolean) = ib => (ib.i, ib.b)

cc2tup(IntBool(3, false))
(tup2cc compose cc2tup)(IntBool(2, true))
// End chunk

// Chunk:  mdoc
enum SumIntBool:
  case SumInt(i: Int)
  case SumBool(b: Boolean)

import SumIntBool.*

val si = SumInt(3)
val sb = SumBool(false)
// End chunk

// Chunk:  mdoc
def echoContents(sib: SumIntBool): String = sib match
  case SumInt(i)  => "Integer: " + i
  case SumBool(b) => "Boolean: " + b

echoContents(si)
echoContents(sb)
// End chunk

// Chunk:  mdoc
val ei: Either[Int, Boolean] = Left(3)
val eb: Either[Int, Boolean] = Right(false)

def echoEither(sib: Either[Int, Boolean]): String = sib match
  case Left(i)  => "Integer: " + i
  case Right(b) => "Boolean: " + b

echoEither(ei)
echoEither(eb)
// End chunk

// Chunk:  mdoc:reset
val si : Int|Boolean = 3
val sb : Int|Boolean = false

def echoContents(sib: Int|Boolean): String = sib match
  case i: Int => "Integer: " + i
  case b: Boolean => "Boolean: " + b

echoContents(si)
echoContents(sb)
// End chunk

