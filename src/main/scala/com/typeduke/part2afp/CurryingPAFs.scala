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

  // Three addition functions/methods
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  /* Exercise 1
   * Obtain an `addSeven` function from each of the three addition functions/methods.
   */

  val addSeven = (x: Int) => simpleAddFunction(x, 7)
  val addSeven2 = (x: Int) => simpleAddMethod(x, 7)
  val addSeven3 = (x: Int) => curriedAddMethod(7)(x)
  val addSeven4 = curriedAddMethod(7)
  val addSeven5 = simpleAddMethod(7, _)
  val addSeven6 = simpleAddMethod(_, 7)
  val addSeven7 = simpleAddFunction.curried(7)

  /* Exercise 2
   * Process a list of numbers and return their string representations in different formats.
   *
   * Step 1: Create a curried formatting method with a formatting string and a value.
   * Step 2: Process a list of numbers with various formats.
   */

  val piWithTwoDec = "%1.2f".format(Math.PI) // 3.14

  def curriedFormatter(fmt: String)(number: Double): String = fmt.format(number)
  val someDecimals = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val formattedDecimals = someDecimals.map(curriedFormatter("%4.2f"))
  val formattedDecimals2 = someDecimals.map(curriedFormatter("%8.6f"))
  val formattedDecimals3 = someDecimals.map(curriedFormatter("%16.14f"))

  // By-name vs. zero-param lambdas and methods vs. functions
  def byName(n: => Int) = n + 1
  def byLambda(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  byName(23) // 24
  byName(method) // 43, no Eta expansion, method invoked here
  byName(parenMethod()) // 43
  // byName(parenMethod) // Doesn't compile, "[...] must be called with () argument"
  byName((() => 42)()) // 43
  // byName(() => 42) // Doesn't compile, "Found: () => Int, Required: Int"

  // byLambda(23) // Doesn't compile, "Found: (23 : Int), Required: () => Int"
  // byLambda(method) // Doesn't compile, Eta expansion not possible here
  byLambda(parenMethod) // 43, Eta expansion done here
  byLambda(() => 42) // 43
  byLambda(() => parenMethod()) // 43

  def main(args: Array[String]): Unit = {
    println(danielsGreeting)
    println(danielsGreeting2)

    println(piWithTwoDec)
    println(formattedDecimals)
    println(formattedDecimals2)
    println(formattedDecimals3)
  }
}
