// Chunk:  mdoc
import cats.*
import cats.implicits.*
import cats.effect.IO
import cats.effect.unsafe.implicits.global

def printMsg(s: String): IO[Unit] = IO{ println(s) }
val io = for
  _ <- printMsg("Hi")
  _ <- printMsg("There")
yield ()
io.unsafeRunSync()
// End chunk

// Chunk:  mdoc
val ioA = printMsg("A")
val ioB = printMsg("B")
val ioC = printMsg("C")
// End chunk

// Chunk:  mdoc
val program = (ioA, ioB, ioC).parMapN { (_, _, _) => () }

program.unsafeRunSync()
// End chunk

