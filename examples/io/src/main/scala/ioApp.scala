/*
ioApp.scala
Stub for Scala Cats IOApp
*/

import cats._
import cats.implicits._
import cats.effect._

object CatsIOApp extends IOApp {

  def printMsg(s: String): IO[Unit] = IO{ println(s) }

  def run(args: List[String]): IO[ExitCode] = {
    val program = for {
      _ <- printMsg("Hi")
      _ <- printMsg("There")
    } yield ()
    println("IO not triggered yet")
    program.as(ExitCode.Success)
  }

}
