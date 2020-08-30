package com.github.gondolav.spotify4s.entities

import upickle.default._

case class ErrorInfo(status: Int, message: String)

object ErrorInfo {
  implicit val rw: ReadWriter[ErrorInfo] = macroRW
}

case class Error(error: ErrorInfo)

object Error {
  implicit val rw: ReadWriter[Error] = macroRW
}
