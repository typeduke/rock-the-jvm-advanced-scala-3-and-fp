package com.typeduke.part2afp

object PartialFunctions {
  val aFunction: Int => Int = x => x + 1

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new RuntimeException("No suitable cases possible")

  // This function is only applicable to the `Int` instances 1, 2 and 5!
  val aFussyFunction2 = (x: Int) =>
    x match {
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

  val anotherPartialFunction: PartialFunction[Int, Int] = { case 45 =>
    86
  }
  val partialFunctionChain = aPartialFunction.orElse[Int, Int](anotherPartialFunction)

  // Higher-order functions accept partial functions...
  // ...because `PartialFunction[A, B]` extends `Function1[A, B]`.
  val aList = List(1, 2, 3, 4)

  val aChangedList = aList.map({
    case 1 => 4
    case 2 => 3
    case 3 => 45
    case 4 => 67
    case _ => 0
  })

  // Simplification thanks to the "single-parameter curly braces pattern":
  val aChangedList2 = aList.map {
    case 1 => 4
    case 2 => 3
    case 3 => 45
    case 4 => 67
    case _ => 0
  }

  case class Person(name: String, age: Int)

  val someKids = List(
    Person("Alice", 3),
    Person("Bobbie", 5),
    Person("Jane", 4)
  )

  // Partial functions are useful to deconstruct data structures right away and not in a lambda.
  val someGrownUpKids = someKids.map { case Person(name, age) =>
    Person(name, age + 1)
  }

  def main(args: Array[String]): Unit = {
    println(aPartialFunction(2)) // 56
    // println(aPartialFunction(33)) // `MatchError`

    println(aLiftedPartialFunction(2)) // `Some(56)`
    println(aLiftedPartialFunction(33)) // `None`

    // 45 is not supported by the first but the second partial function.
    println(partialFunctionChain(45)) // 86

    println(someKids)
    println(someGrownUpKids)
  }
}
