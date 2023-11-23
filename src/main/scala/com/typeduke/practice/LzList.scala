package com.typeduke.practice

// A lazily evaluated, potentially INFINITE linked list
abstract class LzList[A] {
  def isEmpty: Boolean
  def head: A
  def tail: LzList[A]

  // Utilities
  def #::(elem: A): LzList[A] // Prepending
  def ++(other: LzList[A]): LzList[A]

  // "Classics"
  def foreach[B](f: A => Unit): Unit
  def map[B](f: A => B): LzList[B]
  def flatMap[B](f: A => LzList[B]): LzList[B]
  def filter(predicate: A => Boolean): LzList[A]
  def withFilter(predicate: A => Boolean): LzList[A] = this.filter(predicate)

  def take(n: Int): LzList[A] // Takes the first `n` elements from this lazy list

  def toList: List[A] = {
    def toListAux(rem: LzList[A], acc: List[A]): List[A] =
      if (rem.isEmpty) acc.reverse
      else toListAux(rem.tail, rem.head :: acc)

    toListAux(this, List())
  }
}

case class LzEmpty[A]() extends LzList[A] {
  override def isEmpty: Boolean = true
  override def head: A = throw new NoSuchElementException
  override def tail: LzList[A] = throw new NoSuchElementException

  override def #::(elem: A): LzList[A] = new LzCons(elem, this)
  override def ++(other: LzList[A]): LzList[A] = other

  override def foreach[B](f: A => Unit): Unit = ()
  override def map[B](f: A => B): LzList[B] = LzEmpty() // Why not `this`?
  override def flatMap[B](f: A => LzList[B]): LzList[B] = LzEmpty() // Why not `this`?
  override def filter(predicate: A => Boolean): LzList[A] = this

  override def take(n: Int): LzList[A] =
    if (n == 0) this
    else throw new RuntimeException(s"Cannot take $n elements from an empty list")
}

class LzCons[A](hd: => A, tl: => LzList[A]) extends LzList[A] {
  override def isEmpty: Boolean = false

  // Use call by need
  override lazy val head: A = this.hd
  override lazy val tail: LzList[A] = this.tl

  override def #::(elem: A): LzList[A] = new LzCons(elem, this)
  override def ++(other: LzList[A]): LzList[A] = new LzCons(this.head, this.tail ++ other) // TODO

  override def foreach[B](f: A => Unit): Unit = {
    f(this.head)
    tail.foreach(f)
  }

  override def map[B](f: A => B): LzList[B] =
    new LzCons(f(this.head), this.tail.map(f))

  override def flatMap[B](f: A => LzList[B]): LzList[B] =
    f(this.head) ++ this.tail.flatMap(f)

  override def filter(predicate: A => Boolean): LzList[A] =
    if (predicate(this.head)) new LzCons(this.head, this.tail.filter(predicate))
    else this.tail.filter(predicate) // TODO

  override def take(n: Int): LzList[A] =
    if (n <= 0) LzEmpty()
    else if (n == 1) new LzCons(this.head, LzEmpty())
    else new LzCons(this.head, this.tail.take(n - 1))
}

object LzList {
  def empty[A]: LzList[A] = LzEmpty()

  def generate[A](start: A)(generator: A => A): LzList[A] =
    new LzCons(start, LzList.generate(generator(start))(generator))

  def from[A](list: List[A]): LzList[A] = list.foldLeft(this.empty) { (currentLzList, newElem) =>
    new LzCons(newElem, currentLzList)
  }
}

object LzListPlayground {
  def main(args: Array[String]): Unit = {
    val naturals = LzList.generate(1)(_ + 1) // Infinite list of natural numbers
  }
}
