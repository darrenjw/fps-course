// Chunk:  mdoc
import cats._
import cats.implicits._
import cats.effect.IO
def printMsg(s: String): IO[Unit] = IO{ println(s) }
val io = for {
  _ <- printMsg("Hi")
  _ <- printMsg("There")
} yield ()
io.unsafeRunSync()
// End chunk

// Chunk:  mdoc
import cats.effect.ContextShift
import scala.concurrent.ExecutionContext.Implicits.global
implicit val contextShift = IO.contextShift(global)

val ioA = printMsg("A")
val ioB = printMsg("B")
val ioC = printMsg("C")
// End chunk

// Chunk:  mdoc
val program = (ioA, ioB, ioC).parMapN { (_, _, _) => () }

program.unsafeRunSync()
// End chunk

