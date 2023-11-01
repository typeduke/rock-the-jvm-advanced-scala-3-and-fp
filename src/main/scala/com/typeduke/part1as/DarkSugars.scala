package com.typeduke.part1as

object DarkSugars {
  // 1 - Sugar for methods with one parameter
  def singleParamMethod(x: Int): Int = x + 1

  val aMethodCall = singleParamMethod({
    // Long code
    42
  })

  val aMethodCall2 = singleParamMethod {
    // Long code
    42
  }

  // Example: `Try`, `Future`
  import scala.util.Try

  val aTryInstance = Try {
    throw new RuntimeException
  }

  // Example: Higher-order functions
  val anIncrementedList = List(1, 2, 3).map { x =>
    // Code block
    x + 1
  }

  // 2 - Single abstract method pattern (since 2.12)
  trait Action {
    def act(x: Int): Int
    // There may be other IMPLEMENTED fields/methods here.
  }

  val anAction = new Action {
    override def act(x: Int): Int = x + 1
  }

  val anAction2: Action = (x: Int) => x + 1

  // Example: `Runnable`
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hi from another thread!")
  })

  val aThread2 = new Thread(() => println("Hi from another thread!"))

  // 3 - Methods ending in a `:`
  val aList = List(1, 2, 3)
  val aPrependedList = 0 :: aList

  // NOT same as `0.::(aList)`, as `::` isn't a method on the `Int` type...
  // val aThing = 0.::(aList) // This doesn't compile.

  // ...it's a method on the `List` type:
  val aThing = aList.::(0)

  // Methods ending in `:` are right-associative, so the right-most operands are evaluated first.
  val aBigList = 0 :: 1 :: 2 :: List(3, 4) // Same as `List(3, 4).::(2).::(1).::(0)`

  // Example
  class MyStream[T] {
    infix def -->:(value: T): MyStream[T] = this // The implementation isn't important.
  }

  val myStream = 1 -->: 2 -->: 3 -->: 4 -->: new MyStream[Int]

  // 4 - Multi-word identifier
  class Talker(name: String) {
    infix def `and then said`(gossip: String) = println(s"$name said: \"$gossip\"")
  }

  val daniel = new Talker("Daniel")
  val danielsStatement = daniel `and then said` "I love Scala!"

  // Example: HTTP libraries
  object `Content-Type` {
    val `application/json` = "application/json"
  }

  // 5 - Infix types
  import scala.annotation.targetName

  @targetName("Arrow") // For more Java bytecode readability and generally better Java interop
  infix class -->[A, B]
  val compositeType: Int --> String = new -->[Int, String] // This line demoes the infix notation.

  // 6 - `update()`
  val anArray = Array(1, 2, 3)
  anArray.update(2, 45)
  anArray(2) = 45 // Same

  // 7 - Mutable fields
  class Mutable {
    private var internalMember: Int = 0
    def member = this.internalMember // "Getter" in Java terms
    def member_=(value: Int): Unit = { // "Setter"
      this.internalMember = value
    }
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // Rewritten to `aMutableContainer.member_=(42)`

  // 8 - Variable arguments (Varargs)
  def methodWithVarargs(args: Int*) = {
    // Return the number of arguments supplied
    args.length
  }

  val callWithZeroArgs = methodWithVarargs()
  val callWithOneArg = methodWithVarargs(78)
  val callWithTwoArgs = methodWithVarargs(12, 34)

  val aCollection = List(1, 2, 3)
  val callWithDynamicArgs = methodWithVarargs(aCollection*) // `*` signals the unwrapping.

  def main(args: Array[String]): Unit = {}
}
