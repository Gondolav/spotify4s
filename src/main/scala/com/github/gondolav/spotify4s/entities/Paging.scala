package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Paging[T](href: String, items: List[T], limit: Int, next: String, offset: Int, previous: String, total: Int)

object Paging {
  implicit def rw[T: ReadWriter]: ReadWriter[Paging[T]] = macroRW
}