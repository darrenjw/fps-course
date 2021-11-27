/*
io.scala
Stub for Scala Cats IO
*/

import cats.*
import cats.implicits.*
import cats.effect.IO
import cats.effect.unsafe.implicits.global

def printMsg(s: String): IO[Unit] = IO{ println(s) }

def mainIO: IO[Unit] = for
  _ <- printMsg("Hi")
  _ <- printMsg("There")
yield ()

@main def catsApp =
  val io = mainIO
  println("Not triggered IO yet")
  io.unsafeRunSync()
  println("All done")

