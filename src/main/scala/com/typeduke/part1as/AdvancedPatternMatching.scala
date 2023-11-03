package com.typeduke.part1as

import java.{util => ju}

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
    case even()        => "An even number"
    case singleDigit() => "A one-digit number"
    case _             => "No special property"
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
    case _         => "Some uninteresting list"
  }

  // Decomposing sequences
  val aListDecomposed = aList match {
    case List(1, _*) => "A list starting with 1"
    case _           => "Some other list"
  }

  abstract class MyList[A] {
    def head: A = throw new NoSuchElementException
    def tail: MyList[A] = throw new NoSuchElementException
  }

  case class Empty[A]() extends MyList[A]
  case class Cons[A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty()) Some(Seq.empty)
      else unapplySeq(list.tail).map(restOfSequence => list.head +: restOfSequence)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty())))
  val myListDecomposed = myList match {
    case MyList(1, _*) => "A list starting with 1"
    case _             => "Some other list"
  }

  // Custom return type for `unapply` methods (to be used instead of `Option`)
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty = false
      override def get = person.name
    }
  }

  val weirdPersonPatternMatched = daniel match {
    case PersonWrapper(name) => s"Hey, my name is $name."
  }

  def main(args: Array[String]): Unit = {
    println(danielPatternMatched)
    println(danielsLegalStatusPatternMatched)

    println(iPatternMatched)

    println(anEitherPatternMatched)
    println(aListPatternMatched)

    println(aListDecomposed)
    println(myListDecomposed)

    println(weirdPersonPatternMatched)
  }
}
