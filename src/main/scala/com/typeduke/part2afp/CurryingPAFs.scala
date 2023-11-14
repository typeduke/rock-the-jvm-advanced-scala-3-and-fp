package com.typeduke.part2afp

object CurryingPAFs {
  // Currying
  val superAdder: Int => Int => Int =
    x => y => x + y

  val addThree = superAdder(3) // y => 3 + y
  val eight = addThree(5) // 8
  val eightTwo = superAdder(3)(5)

  // Curried methods
  def curriedAdder(x: Int)(y: Int): Int =
    x + y

  // Converting methods to function values
  val addFour = curriedAdder(4) // Eta expansion
  val nine = addFour(5) // 9

  def increment(x: Int): Int = x + 1
  val aList = List(1, 2, 3)
  val anIncrementedList = aList.map(increment) // Eta expansion, again

  // Underscores are powerful, as they allow the shaping of lambdas obtained from methods.
  def concatenator(a: String, b: String, c: String): String = a + b + c
  
  val insertName = concatenator("Hi, I'm ", _: String, ". I'm gonna show you a nice Scala trick.")
  val insertTheBlanks = concatenator(_: String, "Daniel", _: String)

  val danielsGreeting = insertName("Daniel")
  val danielsGreeting2 = insertTheBlanks("Hi, ", ", how are you?")

  // Continue here:
  // Advanced Functional Programming - Currying and Partially Applied Functions - 12:00

  def main(args: Array[String]): Unit = {
    println(danielsGreeting)
    println(danielsGreeting2)
  }
}
