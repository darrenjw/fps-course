# Course outline

The course will provide an introduction to category theory and pure functional programming in Scala, and explain how and why core concepts from category theory can enable the construction of modular, composable, testable and reusable pure functional programs.

The course will use Scala 3 for illustration of concepts and hands-on exercises, together with the Typelevel Cats library, and some minor use of associated Typelevel libraries (such as Cats-effect, Monocle, ScalaCheck and Discipline).

The course will run over two full days, and will be delivered in four parts, each corresponding to half a day. Each part will consist of theory (delivered by lecture accompanied by PDF notes), some illustrative live demonstration, and some hands-on exercises to be attempted by course participants. Some appropriate additional on-line resources will also be provided.


### Part 1

* Types, functions, pure functions, partial functions, referential transparency, reasoning, memoisation, effects, side-effects and composition
* Categories, the Set category, the category of types and functions, isomorphisms, duality and the opposite category
* Products and coproducts: algebraic data types (ADTs)
* Monoids - in algebra and in categories
* Associativity and commutativity: magmas, semigroups, monoids, groups, and abelian groups
* Abstraction, type classes and the type class pattern in Scala

### Part 2

* Functors and endofunctors: covariant and contravariant functors, bifunctors, and profunctors
* Examples of functors: List, Vector
* Functor laws and map-fusion
* Natural transformations
* Monads, monadic composition, for-expressions, the Kleisli category, Kleisli arrows, and the "happy path"
* Monad laws
* Example monads: List, Vector, Option, Reader, Writer, State
* Dubious monads: Try and Future

### Part 3

* Applicative functors: Cartesian, Semigroupal, Applicative, ap, zip, and cartesian closed categories (CCCs)
* Parallel versus sequential composition and the principle of least power
* Foldable and Traverse
* Comonads as the categorical dual of a monad
* Example comonads: Stream and Store
* Lenses and their applications

### Part 4

* Deferred evaluation and free structures
* List as the free monoid
* The Free monad and the interpreter pattern
* The IO monad and pure functional effect handling
* Testing pure functional code
* Composing functors, applicatives and monads
* Monad transformers
* Example transformers: OptionT, ReaderT, WriterT
* ReaderT = Kleisli

