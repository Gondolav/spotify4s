package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Error(status: Int, message: String)
object Error {
  implicit val rw: ReadWriter[Error] = macroRW
}
