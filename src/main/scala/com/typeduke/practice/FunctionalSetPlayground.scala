package com.typeduke.practice

import scala.annotation.tailrec

abstract class FSet[A] extends (A => Boolean) {
  // Main API
  def contains(elem: A): Boolean
  def apply(elem: A): Boolean = this.contains(elem)

  infix def +(elem: A): FSet[A]
  infix def ++(other: FSet[A]): FSet[A]

  // "Classics"
  def map[B](f: A => B): FSet[B]
  def flatMap[B](f: A => FSet[B]): FSet[B]
  def filter(predicate: A => Boolean): FSet[A]
  def foreach(f: A => Unit): Unit
}

case class Empty[A]() extends FSet[A] {
  override def contains(elem: A) = false

  override infix def +(elem: A): FSet[A] = Cons(elem, this)
  override infix def ++(other: FSet[A]): FSet[A] = other

  override def map[B](f: A => B): FSet[B] = Empty()
  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()
  override def filter(predicate: A => Boolean): FSet[A] = this
  override def foreach(f: A => Unit): Unit = ()
}

case class Cons[A](head: A, tail: FSet[A]) extends FSet[A] {
  override def contains(elem: A) =
    elem == this.head || this.tail.contains(elem)

  override infix def +(elem: A): FSet[A] =
    if (this.contains(elem)) this
    else Cons(elem, this)

  override infix def ++(other: FSet[A]): FSet[A] =
    this.tail ++ other + this.head

  override def map[B](f: A => B): FSet[B] =
    this.tail.map(f) + f(this.head)

  override def flatMap[B](f: A => FSet[B]): FSet[B] =
    this.tail.flatMap(f) ++ f(this.head)

  override def filter(predicate: A => Boolean): FSet[A] = {
    val filteredTail = this.tail.filter(predicate)
    if predicate(head) then filteredTail + head // Scala 3's braceless syntax
    else filteredTail
  }

  override def foreach(f: A => Unit): Unit = {
    f(this.head)
    this.tail.foreach(f)
  }
}

object FSet {
  def apply[A](values: A*): FSet[A] = {
    @tailrec
    def buildSet(valuesSeq: Seq[A], acc: FSet[A]): FSet[A] =
      if (valuesSeq.isEmpty) acc
      else buildSet(valuesSeq.tail, acc + valuesSeq.head)

    buildSet(values, Empty())
  }
}

object FunctionalSetPlayground {
  def main(args: Array[String]): Unit = {
    val firstFive = FSet(1, 2, 3, 4, 5)

    println(firstFive.contains(5)) // true
    println(firstFive(6)) // false
    println((firstFive + 10).contains(10)) // true
    println(firstFive.map(_ * 2).contains(10)) // true
    println(firstFive.map(_ % 2).contains(1)) // true
    println(firstFive.flatMap(x => FSet(x, x + 1)).contains(7)) // false
  }
}
