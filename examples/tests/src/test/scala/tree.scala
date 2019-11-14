/*
tree.scala

Example of using "cats-laws" with "discipline" to check that
category theory laws are really satisfied by an instance.

Here, we test that a simple binary tree is _really_ a Functor

Based on the example from here:
https://typelevel.org/cats/typeclasses/lawtesting.html

*/

import cats._
import cats.implicits._

// Define a Tree
sealed trait Tree[+A]
case object Leaf extends Tree[Nothing]
case class Node[A](p: A, left: Tree[A], right: Tree[A]) extends Tree[A]

// Provide evidence that the Tree is a Functor
object Tree {
  implicit val functorTree: Functor[Tree] = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(f: A => B) = tree match {
      case Leaf => Leaf
      case Node(p, left, right) => Node(f(p), map(left)(f), map(right)(f))
    }
  }
}

// Now we want to check if Tree really satisfies the Functor laws...

// Need to either provide ScalaCheck Arbitrary instance (for generating
// random Trees), or let ScalacheckShapeless derive one for you
import org.scalacheck.ScalacheckShapeless._

import cats.laws.discipline.FunctorTests
import org.scalatest.funsuite.AnyFunSuite
import org.typelevel.discipline.scalatest.Discipline

class TreeLawTests extends AnyFunSuite with Discipline {

  // Need an equality for Tree
  implicit def eqTree[A: Eq]: Eq[Tree[A]] = Eq.fromUniversalEquals

  // Now we can just test the Functor laws from cats-laws using discipline
  checkAll("Tree.FunctorLaws", FunctorTests[Tree].functor[Int, Int, String])

}

// eof
