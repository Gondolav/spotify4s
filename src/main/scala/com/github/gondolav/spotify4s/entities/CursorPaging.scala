package com.github.gondolav.spotify4s.entities

import ujson.{Null, Value}
import upickle.default._

case class CursorPaging[T](href: String, items: Option[List[T]] = None, limit: Option[Int] = None, next: Option[String] = None, cursors: Cursor, total: Int)

object CursorPaging {
  private[spotify4s] implicit def rw[T: ReadWriter]: ReadWriter[CursorPaging[T]] = macroRW


  private[spotify4s] implicit def OptionReader[T: Reader]: Reader[Option[T]] = reader[Value].map[Option[T]] {
    case Null => None
    case jsValue => Some(read[T](jsValue))
  }
}

case class Cursor(after: String)

object Cursor {
  private[spotify4s] implicit val rw: ReadWriter[Cursor] = macroRW
}
