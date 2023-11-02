package com.typeduke.part1as

object AdvancedPatternMatching {
  // We know we can use pattern matching with:
  // - constants
  // - objects
  // - wildcards
  // - variables
  // - infix patterns
  // - lists
  // - case classes
  // - etc.

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = // `p match { case Person(s, i) => ... }`
      if (person.age < 21) None
      else Some((person.name, person.age))

    // `unapply` methods can be defined for other types as well.
    def unapply(age: Int): Option[String] = // `i match { case Person(s) => ... }`
      if (age < 21) Some("not legal")
      else Some("legal")
  }

  val daniel = new Person("Daniel", 102)
  
  // This corresponds to checking that `Person.unapply(Daniel)` returns an `Option((n, a))`:
  val danielPatternMatched = daniel match {
    case Person(n, _) => s"Hi there, I'm $n!"
  }

  val danielsLegalStatusPatternMatched = daniel.age match {
    case Person(legalStatus) => s"I'm $legalStatus!"
  }

  // Boolean patterns
  object even {
    def unapply(i: Int) = i % 2 == 0
  }

  object singleDigit {
    def unapply(i: Int) = i > -10 && i < 10
  }

  val i: Int = 43
  val iPatternMatched = i match {
    case even() => "An even number"
    case singleDigit() => "A one-digit number"
    case _ => "No special property"
  }

  // Infix patterns
  infix case class Or[A, B](a: A, b: B)
  
  val anEither = Or(2, "two")
  val anEitherPatternMatched = anEither match {
    case n Or s => s"$n is written as \"$s\"."
  }

  val aList = List(1, 2, 3)
  val aListPatternMatched = aList match {
    case 1 :: rest => "A list starting with 1"
    case _ => "Some uninteresting list"
  }

  // Decomposing sequences
  // Continue here:
  // A Taste of Advanced Scala - Advanced Pattern Matching - 16:00

  def main(args: Array[String]): Unit = {
    println(danielPatternMatched)
    println(danielsLegalStatusPatternMatched)

    println(iPatternMatched)

    println(anEitherPatternMatched)
    println(aListPatternMatched)
  }
}
