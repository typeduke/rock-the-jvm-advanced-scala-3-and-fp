package com.typeduke.part2afp

object PartialFunctions {
  val aFunction: Int => Int = x => x + 1

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new RuntimeException("No suitable cases possible")

  // This function is only applicable to the `Int` instances 1, 2 and 5!
  val aFussyFunction2 = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  // Partial function
  val aPartialFunction: PartialFunction[Int, Int] = { // As if `x => x match { ... }`
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  // Utilities on partial functions
  val canCallOn37 = aPartialFunction.isDefinedAt(37)

  val aLiftedPartialFunction: Int => Option[Int] = aPartialFunction.lift

  val anotherPartialFunction: PartialFunction[Int, Int] = {
    case 45 => 86
  }
  val partialFunctionChain = aPartialFunction.orElse[Int, Int](anotherPartialFunction)

  // Continue here:
  // Advanced Functional Programming - Partial Functions - 09:00

  def main(args: Array[String]): Unit = {
    println(aPartialFunction(2)) // 56
    // println(aPartialFunction(33)) // `MatchError`

    println(aLiftedPartialFunction(2)) // `Some(56)`
    println(aLiftedPartialFunction(33)) // `None`

    // 45 is not supported by the first but the second partial function.
    println(partialFunctionChain(45)) // 86
  }
}
