// Chunk:  mdoc
val a = 3
val b = false
val p = (a, b)
p._1
// End chunk

// Chunk:  mdoc
val tup2cc: ((Int, Boolean)) => IntBool = tup =>
  IntBool(tup._1, tup._2)
val cc2tup: IntBool => (Int, Boolean) = ib => (ib.i, ib.b)

cc2tup(IntBool(3, false))
(tup2cc compose cc2tup)(IntBool(2, true))
// End chunk

// Chunk:  mdoc
sealed trait SumIntBool
case class SumInt(i: Int) extends SumIntBool
case class SumBool(b: Boolean) extends SumIntBool

val si = SumInt(3)
val sb = SumBool(false)
// End chunk

// Chunk:  mdoc
def echoContents(sib: SumIntBool): String = sib match {
  case SumInt(i)  => "Integer: " + i
  case SumBool(b) => "Boolean: " + b
}

echoContents(si)
echoContents(sb)
// End chunk

// Chunk:  mdoc
val ei: Either[Int, Boolean] = Left(3)
val eb: Either[Int, Boolean] = Right(false)

def echoEither(sib: Either[Int, Boolean]): String = sib match {
  case Left(i)  => "Integer: " + i
  case Right(b) => "Boolean: " + b
}

echoEither(ei)
echoEither(eb)
// End chunk

