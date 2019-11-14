// Chunk:  mdoc
case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)
case class Employee(name: String, company: Company)

val employee = Employee("john", Company("awesome inc", 
  Address("london", Street(23, "high street"))))
// End chunk

// Chunk:  mdoc
employee.copy(
  company = employee.company.copy(
   address = employee.company.address.copy(
    street = employee.company.address.street.copy(
     name = employee.company.address.street.name.capitalize
     ))))
// End chunk

// Chunk:  mdoc
import monocle._
import monocle.macros._

GenLens[Employee](_.company.address.street.name).modify(
  _.capitalize)(employee)
// End chunk

// Chunk:  mdoc
case class Dog(name: String, age: Int)
Dog("fido", 3)
val dog2tup = Iso[Dog, (String, Int)](d => (d.name, d.age)){
  case (n, a) => Dog(n, a)}
dog2tup.get(Dog("fido", 3))
dog2tup.reverseGet(("fido", 3))
// End chunk

// Chunk:  mdoc
def list2Vector[A] = Iso[List[A], Vector[A]](_.toVector)(
  _.toList)
list2Vector.get(List(1, 2, 3))
list2Vector.reverseGet(Vector("a", "b"))
// End chunk

// Chunk: 
def modify(f: B => B): A => A =
  reverseGet compose f compose get
// End chunk

// Chunk:  mdoc
val d2tup = GenIso.fields[Dog]
d2tup.get(Dog("rover", 10))
d2tup.reverseGet(("spot", 1))
// End chunk

// Chunk:  mdoc
val dog = Dog("rover", 10)
val dogName = Lens[Dog, String](_.name)(
  n => d => d.copy(name = n))
dogName.get(dog)
dogName.set("Rover")(dog)
dogName.modify(_.capitalize)(dog)
// End chunk

// Chunk:  mdoc
val dogAge = GenLens[Dog](_.age)
val birthday = dogAge.modify(_ + 1)
birthday(dog)
// End chunk

// Chunk:  mdoc:reset:invisible
import monocle._
import monocle.macros._
// End chunk

// Chunk:  mdoc
sealed trait Pet { val name: String; val age: Int }
case class Cat(name: String, age: Int) extends Pet
case class Dog(name: String, age: Int) extends Pet
val petDog = Prism[Pet, Dog]{
  case Dog(n, a) => Some(Dog(n, a))
  case _         => None
  }(identity)
petDog.getOption(Dog("Rover", 10))
petDog.getOption(Cat("Mog", 12))
petDog(Dog("Rover", 10))
// End chunk

// Chunk:  mdoc
val petDog2 = Prism.partial[Pet, Dog]{
  case Dog(n, a) => Dog(n, a) }(identity)
petDog2.getOption(Dog("Rover", 10))
// End chunk

// Chunk:  mdoc
val dogAge = GenLens[Dog](_.age)
val petDogAge = petDog composeLens dogAge
petDogAge.getOption(Dog("Rover", 10))
petDogAge.getOption(Cat("Mog", 12))
// End chunk

// Chunk:  mdoc
petDogAge.set(5)(Dog("Rover", 10))
petDogAge.set(5)(Cat("Mog", 12))
// End chunk

// Chunk:  mdoc
val petCatAge =  GenPrism[Pet, Cat] composeLens 
  GenLens[Cat](_.age)
petCatAge.set(5)(Cat("Mog", 12))
// End chunk

