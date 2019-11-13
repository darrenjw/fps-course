/*
io.scala
Stub for Scala Cats IO
*/

object CatsApp {

  import cats._
  import cats.implicits._
  import cats.effect.IO

  def printMsg(s: String): IO[Unit] = IO{ println(s) }

  def mainIO: IO[Unit] = for {
    _ <- printMsg("Hi")
    _ <- printMsg("There")
  } yield ()

  def main(args: Array[String]): Unit = {
    val io = mainIO
    println("Not triggered IO yet")
    io.unsafeRunSync()
    println("All done")
  }

}
