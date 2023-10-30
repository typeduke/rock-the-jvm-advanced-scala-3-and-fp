package com.typeduke.part1as

import scala.annotation.tailrec
import scala.annotation.nowarn

object Recap {
  // Values, types and expressions
  val aCondition: Boolean = false // Values are constants.
  val anIfExpression = if (aCondition) 42 else 55 // Expressions evaluate to values.
  val aCodeBlock = {
    if (aCondition) 54: @nowarn
    78
  }

  // Types: `Int`, `String`, `Double`, `Boolean`, `Char`, ...
  val theUnit: Unit = println("Hello, Scala!")

  // Functions
  def aFunction(x: Int): Int = x + 1

  // Recursion: stack vs. tail
  @tailrec def factorial(n: Int, acc: Int): Int =
    if (n <= 0) acc
    else factorial(n - 1, n * acc) // Tail recursive, as the recursive call is the last expression

  // Object-oriented programming
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog

  trait Carnivore {
    infix def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override infix def eat(a: Animal): Unit = println("I'm a croc, I eat everything!")
  }

  // Method notation
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // "Operator"/infix position

  // Anonymous classes
  val aCarnivore = new Carnivore {
    override infix def eat(a: Animal): Unit = println("I'm a carnivore.")
  }

  // Generics
  abstract class LList[A] {
    // Type `A` is known inside the implementation here.
  }

  // Singletons and companions
  object LList // Companion object, used for instance-independent ("static") fields/methods

  // Case classes
  case class Person(name: String, age: Int)

  // Enums
  enum BasicColors {
    case RED, GREEN, BLUE
  }

  // Exceptions and `try`-`catch`-`finally`
  def throwSomeException(): Int =
    throw new RuntimeException

  val aPotentialFailure =
    try {
      // Code that may fail
      throwSomeException()
    } catch {
      case e: Exception => "I caught an exception."
    } finally {
      // Closing resources and code that must execute in any case
      println("Some important logs")
    }

  // Functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(x: Int) = x + 1
  }

  val two = incrementer(1)

  // Lambdas
  val incrementer2 = (x: Int) => x + 1

  // Higher-order functions (HOFs)
  // E.g.: `map`, `flatMap` and `filter`
  val incrementedList = List(1, 2, 3).map(incrementer2)

  // `for` comprehensions
  val pairs = for {
    number <- List(1, 2, 3)
    char <- List('a', 'b')
  } yield s"$number-$char"

  // Scala collections: `Seq`, `Array`, `List`, `Vector`, `Map`, `Tuple`, `Set`

  // `Option`, `Try`
  val anOption: Option[Int] = Option(42)

  // Pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case _ => "not important"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n."
  }

  // Scala 3's braceless syntax
  val pairs2 = for
    number <- List(1, 2, 3)
    char <- List('a', 'b')
  yield s"$number-$char"

  // Indentation tokens
  class BracelessAnimal:
    def eat(): Unit =
      println("I'm doing something.")
      println("Me too.")
    end eat
  end BracelessAnimal

  def main(args: Array[String]): Unit = {}
}
