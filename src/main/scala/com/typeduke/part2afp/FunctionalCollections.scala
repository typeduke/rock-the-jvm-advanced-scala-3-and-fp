package com.typeduke.part2afp

object FunctionalCollections {
  // `Set[A]` extends `A => Boolean`.
  val aSet: Set[String] = Set("I", "love", "Scala")
  val setContainsScala = aSet("Scala") // true


  // `Seq[A]` extends `PartialFunction[Int, A]`.
  val aSeq: Seq[Int] = Seq(1, 2, 3)
  val anElement = aSeq(2) // 3
  // val aNonExistentElement = aSeq(100) // Throws an `IndexOutOfBoundsException`

  // `Map[K, V]` extends `PartialFunction[K, V]`.
  val aPhoneBook: Map[String, Int] = Map(
    "Alice" -> 123456,
    "Bob" -> 987654
  )
  val alicesPhoneNumber = aPhoneBook("Alice")
  // val danielsPhoneNumber = aPhoneBook("Daniel") // Throws a `NoSuchElementException`

  def main(args: Array[String]): Unit = {}
}
