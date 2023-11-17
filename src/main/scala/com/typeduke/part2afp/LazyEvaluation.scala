package com.typeduke.part2afp

object LazyEvaluation {
  // `lazy` delays the evaluation of a value until the first use.
  // Evaluation occurs once.
  lazy val x: Int = {
    println("Hello")
    42
  }

  def retrieveMagicValue() = {
    println("Waiting...")
    Thread.sleep(1000)
    42
  }

  // `lazy` use case 1:
  // Call by need

  // Call by name
  def byNameMethod(n: => Int): Int =
    n + n + n + 1

  def demoByName(): Unit = {
    // The following corresponds to `retrieveMagicValue()` + `retrieveMagicValue()` + ... + 1
    println(byNameMethod(retrieveMagicValue()))
  }

  // Call by need = Call by name + lazy values
  // We obtain the combination of delayed evaluation while avoiding repeated evaluation.
  def byNeedMethod(n: => Int): Int = {
    lazy val lazyN = n // Memoization
    lazyN + lazyN + lazyN + 1
  }

  def demoByNeed(): Unit = {
    println(byNeedMethod(retrieveMagicValue()))
  }

  // `lazy` use case 2:
  // `withFilter`

  // Continue here:
  // Advanced Functional Programming - Lazy Evaluation - 8:00

  def main(args: Array[String]): Unit = {
    // `lazy` use case 1
    demoByName()
    demoByNeed()

    // `lazy` use case 2
  }
}
