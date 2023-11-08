package com.typeduke.practice

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

// Continue here:
// Advanced Functional Programming - Functional Collections: A Functional Set - 10:00

case class Cons[A](head: A, tail: FSet[A]) extends FSet[A] {
  override def contains(elem: A) = ???

  override infix def +(elem: A): FSet[A] = ???
  override infix def ++(other: FSet[A]): FSet[A] = ???

  override def map[B](f: A => B): FSet[B] = ???
  override def flatMap[B](f: A => FSet[B]): FSet[B] = ???
  override def filter(predicate: A => Boolean): FSet[A] = ???
  override def foreach(f: A => Unit): Unit = ???
}

object FunctionalSetPlayground {
  def main(args: Array[String]): Unit = {}
}
