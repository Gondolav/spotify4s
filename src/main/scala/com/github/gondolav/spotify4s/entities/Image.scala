package com.github.gondolav.spotify4s.entities

import upickle.default._

case class Image(height: Int, url: String, width: Int)

object Image {
  private[spotify4s] implicit val rw: ReadWriter[Image] = macroRW
}
