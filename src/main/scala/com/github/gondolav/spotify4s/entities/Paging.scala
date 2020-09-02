package com.github.gondolav.spotify4s.entities

import ujson.{Null, Value}
import upickle.default._

case class Paging[T](href: String, items: Option[List[T]] = None, limit: Option[Int] = None, next: Option[String] = None, offset: Option[Int] = None, previous: Option[String] = None, total: Int)

object Paging {
  implicit def rw[T: ReadWriter]: ReadWriter[Paging[T]] = macroRW


  implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}