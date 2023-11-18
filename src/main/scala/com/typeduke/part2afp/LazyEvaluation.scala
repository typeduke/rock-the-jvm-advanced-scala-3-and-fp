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

  def lessThanThirty(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThanTwenty(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)

  def demoFilter(): Unit = {
    val filteredOnce = numbers.filter(lessThanThirty)
    val filteredTwice = filteredOnce.filter(greaterThanTwenty)
    println(filteredTwice)
  }

  def demoWithFilter(): Unit = {
    // `withFilter` uses lazy evaluation internally, only tests elems against predicates on demand.
    val filteredOnce = numbers.withFilter(lessThanThirty)
    val filteredTwice = filteredOnce.withFilter(greaterThanTwenty)
    println(filteredTwice.map(identity)) // Applying `identity` constitutes such demand.
  }

  def demoForComp(): Unit = {
    // For comprehensions use `withFilter` internally.
    val forComp = for {
      n <- numbers if lessThanThirty(n) && greaterThanTwenty(n)
    } yield n // Inevitably, with for comprehensions, map is called, so no manual call is needed.
    println(forComp)
  }

  def main(args: Array[String]): Unit = {
    // `lazy` use case 1
    // demoByName()
    // demoByNeed()

    // `lazy` use case 2
    demoFilter()
    demoWithFilter()
  }
}
